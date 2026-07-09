package com.arogyamed.healthcare.security;

import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/orders")
                || path.startsWith("/api/sos")
                || path.startsWith("/api/ambulances")
                || path.startsWith("/api/users")
                || path.startsWith("/api/patients")
                || path.startsWith("/api/doctors")
                || path.startsWith("/api/pharmacists")) {

            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        // 2. Extract email from token
        userEmail = jwtService.extractEmail(jwt);

        // 3. If email exists & not authenticated already
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userRepository.findByEmail(userEmail).orElse(null);

            if (user != null && jwtService.isTokenValid(jwt, userEmail)) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}