package com.audion.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String REQUEST_QUEUE = "audio_siren_request";
    public static final String RESPONSE_QUEUE = "audio_siren_response";
    public static final String EXCHANGE_NAME = "audio_siren_exchange";

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

}
