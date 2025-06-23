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
            Map<String, Object> resultMap = fastApiClient.classifySound(audioBytes, filename);

            Double probability = 0.0;
            String result = "";

            if(resultMap.containsKey("probability")){
                Object obj = resultMap.get("probability");
                if(obj instanceof Double){
                    probability = (Double)obj;
                }
            }

            if(resultMap.containsKey("result")){
                Object obj = resultMap.get("result");
                result = obj != null ? obj.toString() : "";
            }

            return new AudioAnalysisResponse(probability, result);

        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
            System.out.println("오류!!!!!!!!!!");
            return new AudioAnalysisResponse(0.0, "");
        }
    }
}
