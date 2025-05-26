package com.audion.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FastApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> classifySound(byte[] audioBytes, String filename){
        Resource fileAsResource = new ByteArrayResource(audioBytes){
            @Override
            public String getFilename(){
                return filename;
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=file; filename=" + filename);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String fastApiUrl = "https://f69e-34-106-250-48.ngrok-free.app/classify";

        ResponseEntity<Map> response = restTemplate.postForEntity(
                fastApiUrl,
                requestEntity,
                Map.class
        );

        return response.getBody();
    }

}
