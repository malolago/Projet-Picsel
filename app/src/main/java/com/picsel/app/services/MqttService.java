package com.picsel.app.services;

import jakarta.annotation.PreDestroy;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);
    private final Disposable mqttSubscription;

    public MqttService(Publisher<Message<byte[]>> mqttInFlow) {
        this.mqttSubscription = Flux.from(mqttInFlow).bufferTimeout(100, Duration.ofSeconds(1, 0))
                .flatMap(this::process)
                .subscribe();
    }

    private Mono<Void> process(List<Message<byte[]>> messages) {
        for (Message<byte[]> message : messages) {
            logger.info("Received MQTT message on topic '{}': {}", message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC),
                    new String(message.getPayload(), StandardCharsets.UTF_8));
        }
        return Mono.empty();
    }

    @PreDestroy
    public void stop() {
        mqttSubscription.dispose();
    }
}
