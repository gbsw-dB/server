package com.audion.service;

import com.audion.dto.AudioAnalysisResponse;
import com.audion.rabbitmq.AudioSender;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AudioAnalysisService {

    private final AudioSender audioSender;

    public AudioAnalysisService(AudioSender audioSender) {
        this.audioSender = audioSender;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public AudioAnalysisResponse sendToFastAPI(byte[] audioBytes, String originalFilename){
//        // 파일을 Resource로 포장
//        Resource fileAsResource = new ByteArrayResource(audioBytes) {
//            @Override
//            public String getFilename() {
//                return originalFilename;
//            }
//        };
//
//        // MultipartFormData 준비
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("audio", fileAsResource);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        // FastAPI 주소
//        String fastApiUrl = "http://fastapi-server:8000/analyze";
//
//        // Post 요청 -> JSON 응답 받기
//        ResponseEntity<AudioAnalysisResponse> response = restTemplate.postForEntity(
//                fastApiUrl,
//                requestEntity,
//                AudioAnalysisResponse.class
//        );
//
//        return response.getBody();

        // 오디오 데이터를 메시지 큐에 전송
        audioSender.sendAudio(audioBytes);

        // 실제 분석 결과는 수신 로직 구현 전까지는 임시로 mock 반환
        AudioAnalysisResponse mockResponse = new AudioAnalysisResponse();
        mockResponse.setAudioType("Processing");
        mockResponse.setDangerLevel(-1);
        return mockResponse;
    }

}
