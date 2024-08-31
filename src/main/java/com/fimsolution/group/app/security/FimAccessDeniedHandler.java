package com.fimsolution.group.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class FimAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(FimAccessDeniedHandler.class);
    private final ObjectMapper MAPPER = new ObjectMapper();


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication;
        logger.warn("Access denied for user '{}'. Required roles: '{}'. URI: '{}'",
                userDetails,
                userDetails.getAuthorities(),
                request.getRequestURI());
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        MAPPER.writeValue(response.getOutputStream(), GenericDto.<String>builder().message("").build());
    }
}
