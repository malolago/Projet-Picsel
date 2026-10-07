package com.picsel.app.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfiguration {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    String topic;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    String clientId = "SpringClient-" + UUID.randomUUID();

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { url });
        // options.setUserName("SpringClient");
        // options.setPassword("SpringClient".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                clientId,
                mqttClientFactory(),
                topic);
        adapter.setCompletionTimeout(10000);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        adapter.setQos(1);
        adapter.setQuiescentTimeout(1000);
        adapter.setDisconnectCompletionTimeout(1000);
        return adapter;
    }

    @Bean
    public Publisher<Message<byte[]>> mqttInFlow(MqttPahoMessageDrivenChannelAdapter adapter) {
        return IntegrationFlow.from(adapter)
                .toReactivePublisher(true);
    }
}
