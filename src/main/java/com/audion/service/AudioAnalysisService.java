package com.audion.service;

import com.audion.client.FastApiClient;
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

import java.util.Map;

@Service
public class AudioAnalysisService {

    private final FastApiClient fastApiClient;

    public AudioAnalysisService(FastApiClient fastApiClient){
        this.fastApiClient = fastApiClient;
    }

    public AudioAnalysisResponse sendToFastAPI(byte[] audioBytes, String filename){
        try{
            Map<String, Object> result = fastApiClient.classifySound(audioBytes, filename);
            boolean detail = result.containsKey("result") && Boolean.TRUE.equals(result.get("result"));
            return new AudioAnalysisResponse(detail);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
            System.out.println("오류!!!!!!!!!!");
            return new AudioAnalysisResponse(false);
        }
    }
}
