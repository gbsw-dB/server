package com.audion.auth.domain.jwt;

import com.audion.auth.repository.UserAccountRepository;
import com.audion.auth.domain.CustomUserDetails;
import com.audion.auth.domain.UserAccount;
import com.audion.auth.domain.jwt.entity.JwtAccessToken;
import com.audion.auth.domain.jwt.entity.JwtRefreshToken;
import com.audion.auth.domain.jwt.repository.JwtAccessTokenRepository;
import com.audion.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserAccountRepository userAccountRepository;
    private final JwtAccessTokenRepository jwtAccessTokenRepository;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username = request.getHeader("Username");
        String authorization = request.getHeader("Authorization");
        String headerRefreshToken = request.getHeader("RefreshToken");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        if (username == null) {
            chain.doFilter(request, response);
            return;
        }

        Optional<JwtAccessToken> accessToken = jwtAccessTokenRepository.findById(username);

        if (accessToken.isPresent()) {
            String tokenUsername = accessToken.get().getUsername();

            if (username.equals(tokenUsername) && authorization.equals(accessToken.get().getAccessToken())) {
                UserAccount userAccount = userAccountRepository.findByUsername(username)
                        .orElseThrow(() -> {
                            return new UsernameNotFoundException("해당 사용자 정보를 찾을 수 없습니다.");
                        });

                CustomUserDetails userDetails = new CustomUserDetails(userAccount);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        }

        if (headerRefreshToken != null) {
            Optional<JwtRefreshToken> jwtRefreshToken = jwtRefreshTokenRepository.findById(username);
            if (jwtRefreshToken.isPresent()) {
                JwtRefreshToken refreshToken = jwtRefreshToken.get();
                if (refreshToken.getUsername().equals(username) && refreshToken.getRefreshToken().equals(headerRefreshToken)) {
                    JwtAccessToken newAccessToken = new JwtAccessToken(username, jwtTokenProvider.createAccessToken(username));
                    jwtAccessTokenRepository.save(newAccessToken);
                    response.addHeader("Authorization", "Bearer " + newAccessToken.getAccessToken());
                }
            }
        }

    }

}
