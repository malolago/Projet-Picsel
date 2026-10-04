package com.picsel.app.services.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;

import java.util.UUID;

@Configuration
@EnableIntegration
public class MqttConfiguration {

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { "tcp://broker.mqtt.cool:1883" });
        // options.setUserName("SpringClient");
        // options.setPassword("SpringClient".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "SpringClient-" + UUID.randomUUID(),
                mqttClientFactory(),
                "topic");
        adapter.setCompletionTimeout(10000);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        adapter.setQos(1);
        return adapter;
    }

    @Bean
    public Publisher<Message<byte[]>> mqttInFlow(MqttPahoMessageDrivenChannelAdapter adapter) {
        return IntegrationFlow.from(adapter)
                .toReactivePublisher(true);
    }
}
