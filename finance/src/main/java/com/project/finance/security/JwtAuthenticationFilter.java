package com.project.finance.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtService = jwtService;

        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        // Step 1: Get Authorization header

        final String authorizationHeader =
                request.getHeader("Authorization");


        // Step 2: Check whether header exists
        // and starts with "Bearer "

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }


        // Step 3: Extract JWT token

        final String jwtToken =
                authorizationHeader.substring(7);


        // Step 4: Extract email from JWT

        final String email =
                jwtService.extractEmail(jwtToken);


        // Step 5: Check whether email exists
        // and user is not already authenticated

        if (email != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {


            // Step 6: Load user from database

            UserDetails userDetails =
                    customUserDetailsService
                            .loadUserByUsername(email);


            // Step 7: Validate JWT token

            if (jwtService.isTokenValid(
                    jwtToken,
                    userDetails.getUsername())) {


                // Step 8: Create Authentication Object

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(

                                userDetails,

                                null,

                                userDetails.getAuthorities()
                        );


                // Step 9: Store authentication
                // inside Spring Security Context

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authenticationToken);
            }
        }


        // Step 10: Continue request

        filterChain.doFilter(request, response);
    }
}
