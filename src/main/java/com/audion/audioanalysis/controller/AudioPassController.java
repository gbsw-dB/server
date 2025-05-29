package com.audion.audioanalysis.controller;

import com.audion.audioanalysis.dto.AudioAnalysisResponse;
import com.audion.audioanalysis.service.AudioAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio")
public class AudioPassController {

    private final AudioAnalysisService audioAnalysisService;

    @Autowired
    public AudioPassController(AudioAnalysisService audioAnalysisService) {
        this.audioAnalysisService = audioAnalysisService;
    }

    @PostMapping("/upload")
    public ResponseEntity<AudioAnalysisResponse> uploadAudio(@RequestParam("file") MultipartFile file){
        try{
            // FastAPI로 전송 및 분석 결과 받기
            AudioAnalysisResponse response = audioAnalysisService.sendToFastAPI(
                    file.getBytes(),
                    file.getOriginalFilename()
            );

            // Flutter로 json 반환
            return ResponseEntity.ok(response);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
