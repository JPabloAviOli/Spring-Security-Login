package com.pavila.TaskProyect.config.security;

import com.pavila.TaskProyect.config.security.filter.JwtAuthenticationFilter;
import com.pavila.TaskProyect.model.util.Role;
import com.pavila.TaskProyect.model.util.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(HttpSecurityConfig::buildRequestMatchers)
                .exceptionHandling( exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                })
                .build();
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {

        authRequest.requestMatchers(HttpMethod.GET, "task/all")
                //.hasAuthority(RolePermission.READ_ALL_TASKS.name())
                .hasRole(Role.USER.name());

        authRequest.requestMatchers(HttpMethod.GET, "task/{id}")
                //.hasAuthority(RolePermission.READ_ONE_TASK.name());
                .hasRole(Role.USER.name());

        authRequest.requestMatchers(HttpMethod.POST, "task/create")
                //.hasAuthority(RolePermission.CREATE_ONE_TASK.name());
                .hasRole(Role.USER.name());

        authRequest.requestMatchers(HttpMethod.PUT, "task/update/{id}")
                //.hasAuthority(RolePermission.UPDATE_ONE_TASK.name());
                .hasRole(Role.USER.name());

        authRequest.requestMatchers(HttpMethod.DELETE, "task/delete/{id}")
                //.hasAuthority(RolePermission.DELETE_ONE_TASK.name());
                .hasRole(Role.USER.name());

        authRequest.requestMatchers(HttpMethod.PUT, "task/{id}/completed")
                //.hasAuthority(RolePermission.COMPLETED_ONE_TASK.name());
                .hasRole(Role.USER.name());


        authRequest.requestMatchers(HttpMethod.GET,"user/all")
                //.hasAuthority(RolePermission.READ_ALL_USERS.name());
                .hasRole(Role.ADMINISTRATOR.name());

        //Autorización de endpoints públicos
        authRequest.requestMatchers(HttpMethod.POST, "user/registered").permitAll();
        authRequest.requestMatchers(HttpMethod.POST, "auth/login").permitAll();
        authRequest.requestMatchers(HttpMethod.GET, "auth/validate-token").permitAll();




        authRequest.anyRequest().authenticated();
    }


}
