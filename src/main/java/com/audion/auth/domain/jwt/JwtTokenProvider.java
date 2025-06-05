package com.audion.auth.domain.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String accessSecretKey;
    private final String refreshSecretKey;
    private final String issuer;

    private final long accessTokenValidTime = 10 * 60 * 1000L;
    private final long refreshTokenValidTime = 10 * 24 * 60 * 60 * 1000L;

    public JwtTokenProvider(
            @Value("${jwt.access_secret_key}") String accessSecretKey,
            @Value("${jwt.refresh_secret_key}") String refreshSecretKey,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.accessSecretKey = accessSecretKey;
        this.refreshSecretKey = refreshSecretKey;
        this.issuer = issuer;
    }

    public String createAccessToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();
    }

    public String createRefreshToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();
    }

    public String getUserPkFromAccessToken(String accessToken) {
        return Jwts.parser()
                .setSigningKey(accessSecretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getBody()
                .getSubject();
    }

    public String getUserPkFromRefreshToken(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(refreshSecretKey)
                .build()
                .parseSignedClaims(refreshToken)
                .getBody()
                .getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, true);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, false);
    }

    private boolean validateToken(String token, boolean isAccess) {
        try {
            String key = isAccess ? accessSecretKey : refreshSecretKey;
            Jwts.parser().setSigningKey(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
