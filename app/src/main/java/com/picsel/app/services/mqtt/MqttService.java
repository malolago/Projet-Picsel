package com.picsel.app.services.mqtt;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import reactor.core.publisher.Flux;

@Service
public class MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    public MqttService(Publisher<Message<byte[]>> mqttInFlow) {
        Flux.from(mqttInFlow).subscribe(
                message -> logger.info("Received MQTT message on topic '{}': {}",
                        message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC),
                        new String(message.getPayload(), StandardCharsets.UTF_8)),
                error -> logger.error("MQTT message stream failed", error));
    }
}
