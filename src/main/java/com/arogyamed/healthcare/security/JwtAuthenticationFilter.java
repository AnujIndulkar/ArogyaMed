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

//        if (path.startsWith("/api/auth")
//                ||path.startsWith("/api/orders")
//                || path.startsWith("/api/sos")
//                || path.startsWith("/api/ambulances")
//                || path.startsWith("/api/users")
//                || path.startsWith("/api/patients")
//                || path.startsWith("/api/doctors")
//                || path.startsWith("/api/pharmacists")
//                || path.startsWith("/api/audit-logs")
//                || path.startsWith("/api/barcodes")
//                || path.startsWith("/api/reports")
//                ||path.startsWith("/api/documents")) {
//
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (path.startsWith("/api/auth")
                || path.startsWith("/api/users")
                || path.startsWith("/api/patients")
                || path.startsWith("/api/doctors")
                || path.startsWith("/api/pharmacists")
                || path.startsWith("/api/wholesalers")
                || path.startsWith("/api/companies")
                || path.startsWith("/api/delivery-partners")
                || path.startsWith("/api/medicines")
                || path.startsWith("/api/inventories")
                || path.startsWith("/api/prescriptions")
                || path.startsWith("/api/medical-records")
                || path.startsWith("/api/appointments")
                || path.startsWith("/api/sos")
                || path.startsWith("/api/ambulances")
                || path.startsWith("/api/orders")
                || path.startsWith("/api/order-items")
                || path.startsWith("/api/payments")
                || path.startsWith("/api/delivery-tracking")
                || path.startsWith("/api/notifications")
                || path.startsWith("/api/kyc")
                || path.startsWith("/api/reviews")
                || path.startsWith("/api/admins")
                || path.startsWith("/api/quality-checks")
                || path.startsWith("/api/dashboard")
                || path.startsWith("/api/audit-logs")
                || path.startsWith("/api/barcodes")
                || path.startsWith("/api/reports")
                || path.startsWith("/api/documents")) {

            System.out.println("Bypassing JWT for: " + path);
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