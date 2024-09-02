package com.pavila.SecureLogin.repository.security;

import com.pavila.SecureLogin.model.entity.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
