package com.pavila.SecureLogin.config.security;

import com.pavila.SecureLogin.exception.ObjectNotFoundException;
import com.pavila.SecureLogin.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeansInjector {

    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (){

        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());

        return authenticationStrategy;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public UserDetailsService userDetailsService(){
        return (usernameEmail -> userRepository.findByEmail(usernameEmail)
                .orElseThrow(()-> new ObjectNotFoundException("User not found with email " + usernameEmail)));
    }


}
