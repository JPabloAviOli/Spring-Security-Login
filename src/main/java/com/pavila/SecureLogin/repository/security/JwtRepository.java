package com.pavila.SecureLogin.repository.security;

import com.pavila.SecureLogin.model.entity.security.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Optional<Jwt> findByToken(String jwt);
}
