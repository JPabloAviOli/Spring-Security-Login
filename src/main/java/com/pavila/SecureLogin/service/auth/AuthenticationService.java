package com.pavila.SecureLogin.service.auth;

import com.pavila.SecureLogin.config.filter.JwtAuthenticationFilter;
import com.pavila.SecureLogin.exception.InvalidPasswordException;
import com.pavila.SecureLogin.exception.ObjectNotFoundException;
import com.pavila.SecureLogin.model.dto.ChangePasswordRequest;
import com.pavila.SecureLogin.model.dto.RegisteredUserRequest;
import com.pavila.SecureLogin.model.dto.auth.AuthenticationRequest;
import com.pavila.SecureLogin.model.dto.auth.AuthenticationResponse;
import com.pavila.SecureLogin.model.entity.security.Jwt;
import com.pavila.SecureLogin.model.entity.security.Role;
import com.pavila.SecureLogin.model.entity.security.Token;
import com.pavila.SecureLogin.model.entity.security.User;
import com.pavila.SecureLogin.repository.security.JwtRepository;
import com.pavila.SecureLogin.util.EmailTemplateName;
import com.pavila.SecureLogin.repository.security.RoleRepository;
import com.pavila.SecureLogin.repository.security.TokenRepository;
import com.pavila.SecureLogin.repository.security.UserRepository;
import com.pavila.SecureLogin.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final JwtRepository jwtRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final EmailService emailService;

    @Value("${security.mail.frontend.activation-url}")
    private String activationUrl;
    @Value("${security.mail.frontend.reset.password-url}")
    private String resetPassword;


    public void registerUser(RegisteredUserRequest registeredUserRequest) throws MessagingException {
        validatePassword(registeredUserRequest.getPassword(), registeredUserRequest.getConfirmationPassword());
        Role role = roleRepository.findByName("USER").orElseThrow(
                () -> new ObjectNotFoundException("Role user was not initialized"));
        User user = User.builder()
                .firstname(registeredUserRequest.getFirstname())
                .lastname(registeredUserRequest.getLastname())
                .email(registeredUserRequest.getEmail())
                .password(passwordEncoder.encode(registeredUserRequest.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(role))
                .build();

        userRepository.save(user);
        log.info("Registered new user with email: {}", registeredUserRequest.getEmail());
        sendValidationEmail(user);
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

        User userDetails = userService.findByEmail(authRequest.getEmail()).get();
        log.info("User authenticated successfully with email: {}", authRequest.getEmail());

        String jwt = jwtService.generateToken(userDetails, generateExtraClaims(userDetails));
        saveUserJwtToken(userDetails, jwt);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }


    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            log.info("Activation token expired. New token sent to email: {}", savedToken.getUser().getEmail());
            throw new ObjectNotFoundException("Activation token has expired. A new token has been send to the same email address");
        }

        if (savedToken.getValidatedAt() != null || savedToken.isUsed()) {
            log.warn("The token has already been used or validated: {}", token);
            throw new IllegalArgumentException("The token has already been used.");
        }

        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        savedToken.setUsed(true);
        tokenRepository.save(savedToken);

        log.info("User account activated for email: {}", user.getEmail());
    }

    public void resetPassword(String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with email" + email));
        try {
            sendResetPasswordEmail(user);
            log.info("Reset password email sent to: {}", email);
        } catch (MessagingException e) {
            log.error("Failed to send the password reset email to: {}", email, e);
            throw new RuntimeException("Failed to send the password reset email.", e);
        }

    }

    public void updatePassword(String token, String newPassword) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendResetPasswordEmail(savedToken.getUser());
            log.info("Password reset token expired. New token sent to email: {}", savedToken.getUser().getEmail());
            throw new ObjectNotFoundException("The token has expired. A new token has been sent to the same email address.");
        }
        if (savedToken.getValidatedAt() != null || savedToken.isUsed()) {
            log.warn("The password reset token has already been used or validated: {}", token);
            throw new IllegalArgumentException("The token has already been used.");
        }
        User user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        savedToken.setUsed(true);
        tokenRepository.save(savedToken);
        log.info("Password updated successfully for user: {}", user.getEmail());
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.warn("Invalid current password for user: {}", user.getEmail());
            throw new InvalidPasswordException("Wrong password");
        }
        validatePassword(request.getNewPassword(), request.getConfirmationPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Password changed successfully for user: {}", user.getEmail());
    }

    public void logout(HttpServletRequest httpServletRequest) {

        String jwt = jwtService.extractJwtFromRequest(httpServletRequest);

        if (!StringUtils.hasText(jwt)) return;
        Optional<Jwt> token = jwtRepository.findByToken(jwt);

        if (token.isPresent() && token.get().isValid()) {
            token.get().setValid(false);
            jwtRepository.save(token.get());
            log.info("JWT token invalidated: {}", jwt);
        }

    }

    private void validatePassword(String password, String confirmationPassword) {

        if (!StringUtils.hasText(password) || !StringUtils.hasText(confirmationPassword)) {
            throw new InvalidPasswordException("Password and confirmation password cannot be empty");
        }

        if (!password.equals(confirmationPassword)) {
            throw new InvalidPasswordException("Passwords don't match");
        }

    }

    private void saveUserJwtToken(User usuarioDetalles, String jwt) {
        Jwt jwtToken = Jwt.builder()
                .token(jwt)
                .user(usuarioDetalles)
                .expiration(jwtService.extractExpiration(jwt))
                .isValid(true)
                .build();
        jwtRepository.save(jwtToken);
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getFullName());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationOrResetPasswordToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationOrResetPasswordToken(User user) {
        //generate token
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int l) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < l; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public void sendResetPasswordEmail(User user) throws MessagingException {
        String resetToken = generateAndSaveActivationOrResetPasswordToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.RESET_PASSWORD,
                resetPassword,
                resetToken,
                "Reset password"
        );
    }

    public boolean isJwtValid(String jwt) {
        Optional<Jwt> token = jwtRepository.findByToken(jwt);
        return jwtAuthenticationFilter.validateToken(token);
    }
}
