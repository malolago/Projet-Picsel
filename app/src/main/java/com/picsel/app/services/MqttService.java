package com.picsel.app.services;

import jakarta.annotation.PreDestroy;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picsel.app.entity.Measure;
import com.picsel.app.repositories.MeasureRepository;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);
    private final ObjectMapper objectMapper;
    private final MeasureRepository repository;
    private final Disposable mqttSubscription;

    public MqttService(Publisher<Message<byte[]>> mqttInFlow, MeasureRepository repository, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.repository = repository;
        this.mqttSubscription = Flux.from(mqttInFlow).bufferTimeout(100, Duration.ofSeconds(1, 0))
                // .flatMap(this::process)
                .flatMap(messages -> process(messages).subscribeOn(Schedulers.boundedElastic()))
                .subscribe();
    }

    private Mono<Void> process(List<Message<byte[]>> messages) throws JacksonException {
        ArrayList<Measure> measures = new ArrayList<>();
        for (Message<byte[]> message : messages) {
            String json = new String(message.getPayload(), StandardCharsets.UTF_8);
            logger.info(json);

            try {
                Measure measure = objectMapper.readValue(json, Measure.class);
                measures.add(measure);
            } catch (JacksonException e) {
                logger.error("Unable to deserialize MQTT message: {}", json, e);
            }

            if (!measures.isEmpty()) {
                this.save(measures);
            }
        }
        return Mono.empty();
    }

    @Transactional
    public void save(List<Measure> measures) {
        repository.saveAll(measures);
    }

    @PreDestroy
    public void stop() {
        mqttSubscription.dispose();
    }
}
