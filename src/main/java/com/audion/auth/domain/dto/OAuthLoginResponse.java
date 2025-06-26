package com.audion.auth.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema
@Data
@AllArgsConstructor
public class OAuthLoginResponse {
    String username;
    String accessToken;
    String refreshToken;
}
