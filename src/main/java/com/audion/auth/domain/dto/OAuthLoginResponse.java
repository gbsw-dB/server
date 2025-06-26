package com.audion.auth.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "OAuth 로그인 응답")
@Data
@AllArgsConstructor
public class OAuthLoginResponse {
    String username;
    String accessToken;
    String refreshToken;
}
