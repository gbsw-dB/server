package com.audion.auth.domain.jwt.repository;

import com.audion.auth.domain.jwt.entity.JwtAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtAccessTokenRepository extends CrudRepository<JwtAccessToken, String> {
}
