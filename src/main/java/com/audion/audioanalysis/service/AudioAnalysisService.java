package com.audion.audioanalysis.service;

import com.audion.audioanalysis.client.FastApiClient;
import com.audion.audioanalysis.dto.AudioAnalysisResponse;
import org.springframework.stereotype.Service;

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
