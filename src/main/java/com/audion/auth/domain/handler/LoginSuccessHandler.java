package com.audion.auth.domain.handler;

import com.audion.auth.domain.dto.OAuthLoginResponse;
import com.audion.auth.domain.jwt.JwtTokenProvider;
import com.audion.auth.domain.jwt.entity.JwtRefreshToken;
import com.audion.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String username = authentication.getName();
        String accessToken = jwtTokenProvider.createAccessToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        jwtRefreshTokenRepository.save(new JwtRefreshToken(username, refreshToken));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        OAuthLoginResponse result = new OAuthLoginResponse(username, accessToken, refreshToken);
        String json = objectMapper.writeValueAsString(result);

        response.getWriter().write(json);

    }

}
