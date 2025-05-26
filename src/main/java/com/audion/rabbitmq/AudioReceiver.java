package com.audion.rabbitmq;

import com.audion.dto.AudioAnalysisResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AudioReceiver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.RESPONSE_QUEUE)
    public void receiveMessage(String jsonMessage) {
        try {
            AudioAnalysisResponse response = objectMapper.readValue(jsonMessage, AudioAnalysisResponse.class);
            // 받은 데이터를 플러터로 전송하는 로직
        } catch (Exception e) {
            System.err.println("JSON 파싱 실패: " + e.getMessage());
        }
    }
}