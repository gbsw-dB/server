package com.audion.auth.controller;

import com.audion.auth.domain.dto.OAuthLoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuth Login", description = "OAuth2 로그인 관련 API")
@RestController
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    @GetMapping("/kakao")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/code/kakao")
    public ResponseEntity<OAuthLoginResponse> callback(@RequestParam String code) {
        OAuthLoginResponse example = new OAuthLoginResponse(
                "kakao_12345",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        );
        return ResponseEntity.ok(example);
    }

}