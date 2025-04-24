package com.audion.auth.domain.handler;

import com.audion.auth.domain.jwt.JwtTokenProvider;
import com.audion.auth.domain.jwt.entity.JwtAccessToken;
import com.audion.auth.domain.jwt.entity.JwtRefreshToken;
import com.audion.auth.domain.jwt.repository.JwtAccessTokenRepository;
import com.audion.auth.domain.jwt.repository.JwtRefreshTokenRepository;
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

    private final JwtAccessTokenRepository jwtAccessTokenRepository;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final int accessTokenValidTime = 10 * 60;
    private final int refreshTokenValidTime = 10 * 24 * 60 * 60;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String username = authentication.getName();
        String accessToken = jwtTokenProvider.createAccessToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        jwtAccessTokenRepository.save(new JwtAccessToken(username, accessToken));
        jwtRefreshTokenRepository.save(new JwtRefreshToken(username, refreshToken));

        response.addHeader("Username", username);
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("RefreshToken", refreshToken);
        response.sendRedirect("/");

    }

}
