package com.audion.audioanalysis.controller;

import com.audion.audioanalysis.dto.AudioAnalysisResponse;
import com.audion.audioanalysis.service.AudioAnalysisService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "FastAPI upload", description = "FastAPI로 전송 및 분석 결과 받기")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<AudioAnalysisResponse> uploadAudio(
            @Parameter(description = "업로드할 오디오 파일", required = true)
            @RequestParam("audio") MultipartFile file){
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
