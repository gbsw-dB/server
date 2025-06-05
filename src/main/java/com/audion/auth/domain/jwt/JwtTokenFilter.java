package com.audion.auth.domain.jwt;

import com.audion.auth.repository.UserAccountRepository;
import com.audion.auth.domain.CustomUserDetails;
import com.audion.auth.domain.jwt.entity.JwtRefreshToken;
import com.audion.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserAccountRepository userAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String accessHeader = request.getHeader("Authorization");
        String refreshHeader = request.getHeader("RefreshToken");

        if (accessHeader != null && accessHeader.startsWith("Bearer ")) {
            String accessToken = accessHeader.substring(7);

            if (jwtTokenProvider.validateAccessToken(accessToken)) {
                String username = jwtTokenProvider.getUserPkFromAccessToken(accessToken);

                userAccountRepository.findByUsername(username).ifPresent(userAccount -> {
                    CustomUserDetails userDetails = new CustomUserDetails(userAccount);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

                chain.doFilter(request, response);
                return;
            }
        }

        if (refreshHeader != null) {
            try {

                if (jwtTokenProvider.validateRefreshToken(refreshHeader)) {
                    String username = jwtTokenProvider.getUserPkFromRefreshToken(refreshHeader);
                    Optional<JwtRefreshToken> optionalRefreshToken = jwtRefreshTokenRepository.findById(username);

                    if (optionalRefreshToken.isPresent() &&
                            optionalRefreshToken.get().getRefreshToken().equals(refreshHeader)) {

                        String newAccessToken = jwtTokenProvider.createAccessToken(username);
                        response.setHeader("Authorization", "Bearer " + newAccessToken);
                    }
                }
            } catch (Exception e) {
            }
        }

        chain.doFilter(request, response);
    }
}
