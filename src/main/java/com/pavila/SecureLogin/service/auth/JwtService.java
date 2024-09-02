package com.pavila.SecureLogin.service.auth;

import com.pavila.SecureLogin.model.entity.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${security.jwt.expiration.in.minutes}")
    private Long EXPIRATION_IN_MINUTES;
    @Value("${security.jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken(User user, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime() );

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();

    }

    public boolean isTokenValid(String jwt, User user) {
        final String username = extractUsername(jwt);
        return (username.equals(user.getUsername())) && !isTokenExpired(jwt);

    }

    private boolean isTokenExpired(String jwt){
        return extractExpiration(jwt).before(new Date());
    }

    public Date extractExpiration(String jwt) {
        return extractClaims(jwt, Claims::getExpiration);
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);

    }

    public String extractUsername(String jwt) {
        return extractClaims(jwt, Claims::getSubject);
    }

    public <T> T extractClaims(String jwt, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }

    public String extractJwtFromRequest(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            return null;
        }

        return authorizationHeader.split(" ")[1];
    }
}
