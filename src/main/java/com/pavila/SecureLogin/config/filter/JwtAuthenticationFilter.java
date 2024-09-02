package com.pavila.SecureLogin.config.filter;

import com.pavila.SecureLogin.exception.ObjectNotFoundException;
import com.pavila.SecureLogin.model.entity.security.Jwt;
import com.pavila.SecureLogin.repository.security.JwtRepository;
import com.pavila.SecureLogin.service.UserService;
import com.pavila.SecureLogin.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;
    private final JwtRepository jwtRepository;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Inside the JSON Web Token Authentication Filter");

        String jwtToken = jwtService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional<Jwt> token = jwtRepository.findByToken(jwtToken);

        boolean isValid = validateToken(token);

        if (!isValid) {
            log.warn("Invalid token: {}", jwtToken);
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extractUsername(jwtToken);

        log.info("Authenticating user with email: {}", email);

        UserDetails user = userService.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado con el email proporcionado"));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("Authentication successful for user: {}", user.getUsername());

        filterChain.doFilter(request, response);

    }

    public boolean validateToken(Optional<Jwt> jwtToken) {
        if (jwtToken.isEmpty()) {
            return false;
        }
        Jwt token = jwtToken.get();
        Date now = new Date(System.currentTimeMillis());

        boolean isValid = token.isValid() && token.getExpiration().after(now);
        if (!isValid) {
            updateTokenStatus(token);
        }
        return isValid;
    }

    private void updateTokenStatus(Jwt token) {
        log.info("Updating token status to invalid for token: {}", token.getToken());
        token.setValid(false);
        jwtRepository.save(token);
    }
}
