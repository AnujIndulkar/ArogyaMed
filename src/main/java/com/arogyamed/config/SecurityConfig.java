package com.arogyamed.config;

import com.arogyamed.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CorsConfigurationSource corsConfigurationSource) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // not logged in -> 401, so the frontend sends the user to /login
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // login + register
                        .requestMatchers("/api/auth/**").permitAll()

                        // registration steps happen BEFORE the user has a token
                        .requestMatchers(HttpMethod.POST,
                                "/api/patients", "/api/doctors", "/api/pharmacists",
                                "/api/wholesalers", "/api/companies", "/api/delivery-partners",
                                "/api/quality-inspectors", "/api/ambulances",
                                "/api/documents/upload").permitAll()

                        // medicine images and the medicine catalog can be viewed without logging in
                        .requestMatchers(HttpMethod.GET, "/files/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/medicines/**").permitAll()

                        // admin-only areas
                        .requestMatchers("/api/admins/**", "/api/audit-logs/**").hasRole("ADMIN")

                        // only companies (and admins) may change medicines
                        .requestMatchers(HttpMethod.POST, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")

                        // everything else needs a valid login
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}