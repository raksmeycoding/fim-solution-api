package com.fimsolution.group.app.filters;

import com.fimsolution.group.app.security.JwtService;
import com.fimsolution.group.app.security.UserDetailsServiceImpl;
import com.fimsolution.group.app.utils.FilterHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userService;

    @Value("${jwt.cookieName}")
    private String jwtCookiesName;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("logger servlet path::{}", request.getServletPath());


        if (FilterHelper.filterUrlContains(request)) {
            filterChain.doFilter(request, response);
            logger.info("passed JwtAuthenticationFilter");
            return;
        }

        String token = null;
        String username = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(jwtCookiesName)) {
                    token = cookie.getValue();
                }
            }
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }


        username = jwtService.extractUserName(token);

        if (username != null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            logger.info("Authorities:{}", userDetails.getAuthorities());
            if (jwtService.isTokenValid(token, userDetails)) {

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);  // <-- Add this line to set the SecurityContextHolder

                logger.info("User {}, role, {}, is trying to access {}", userDetails.getUsername(), userDetails.getAuthorities(), request.getRequestURI());


            }
        }


//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        userEmail = jwtService.extractUserName(jwt);
//        if (StringUtils.isNotEmpty(userEmail)
//                && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userService
//                    .loadUserByUsername(userEmail);
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//                SecurityContext context = SecurityContextHolder.createEmptyContext();
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                context.setAuthentication(authToken);
//                SecurityContextHolder.setContext(context);
//            }
//        }


        filterChain.doFilter(request, response);
    }


}