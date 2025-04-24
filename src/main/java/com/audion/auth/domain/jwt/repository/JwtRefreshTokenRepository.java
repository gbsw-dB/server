package com.audion.auth.domain.jwt.repository;

import com.audion.auth.domain.jwt.entity.JwtRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtRefreshTokenRepository extends CrudRepository<JwtRefreshToken, String> {
}
