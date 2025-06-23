package com.audion.message.rabbitmq;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "features.rabbitmq-enabled", havingValue = "true")
@Component
public class AudioSender {

    private final RabbitTemplate rabbitTemplate;
    private static final String ROUTING_KEY = "audio.siren.request";

    public AudioSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAudio(byte[] audioData) {
        // audio_siren_request 큐로 바이너리 데이터 전송
        rabbitTemplate.convertAndSend(RabbitMQConfig.REQUEST_QUEUE, audioData);
    }

    @PostConstruct
    public void testSendAudio(){
        byte[] fakeAudio = "dummy audio bytes".getBytes();
        sendAudio(fakeAudio);
    }
}