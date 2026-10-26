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

                        // quality inspectors: only they (and admins for the raw records) can touch inspections
                        .requestMatchers("/api/inspections/**").hasRole("QUALITY_INSPECTOR")
                        .requestMatchers("/api/quality-checks/**").hasAnyRole("QUALITY_INSPECTOR", "ADMIN")

                        // ambulance providers: their own dashboard API. The raw ambulance and booking
                        // endpoints can change anyone's data, so they are admin-only.
                        .requestMatchers("/api/ambulance-provider/**").hasRole("AMBULANCE_PROVIDER")
                        .requestMatchers("/api/ambulance-bookings/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/ambulances/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/ambulances/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/ambulances/**").hasRole("ADMIN")

                        // only companies (and admins) may change medicines
                        .requestMatchers(HttpMethod.POST, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/medicines/**").hasAnyRole("COMPANY", "ADMIN")

                        // ---- orders ----
                        // patients: place, pay, list their own, cancel their own
                        .requestMatchers(HttpMethod.POST, "/api/orders/checkout", "/api/orders/*/pay", "/api/orders/*/cancel").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/api/orders/my").hasRole("PATIENT")
                        // pharmacists: see unclaimed orders, accept one, see their own
                        // (these must stay ABOVE the broad GET /api/orders/** rule below)
                        .requestMatchers(HttpMethod.GET, "/api/orders/available", "/api/orders/assigned").hasRole("PHARMACIST")
                        .requestMatchers(HttpMethod.POST, "/api/orders/*/accept").hasRole("PHARMACIST")
                        // staff: change status, and the old raw endpoints
                        .requestMatchers(HttpMethod.PATCH, "/api/orders/*/status").hasAnyRole("ADMIN", "PHARMACIST")
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasAnyRole("ADMIN", "PHARMACIST")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAnyRole("ADMIN", "PHARMACIST")
                        // the full order list and search: staff only, so a patient cannot see other patients' orders
                        .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("ADMIN", "PHARMACIST", "WHOLESALER", "DELIVERY_PARTNER")

                        // ---- stock (pharmacist inventory page) ----
                        .requestMatchers("/api/stock/**").hasAnyRole("PHARMACIST", "ADMIN")

                        // ---- payments ----
                        // the raw payment endpoints are for admins only: otherwise anyone could
                        // mark their own payment as SUCCESS. Patients pay through /api/orders/{id}/pay.
                        .requestMatchers(HttpMethod.POST, "/api/payments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/payments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/payments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/payments/**").hasAnyRole("ADMIN", "PHARMACIST")

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