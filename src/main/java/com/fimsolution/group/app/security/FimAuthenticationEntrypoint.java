package com.fimsolution.group.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.RespondDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FimAuthenticationEntrypoint implements AuthenticationEntryPoint {

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        MAPPER.writeValue(response.getOutputStream(), RespondDto.<String>builder().data("1").message(HttpStatus.UNAUTHORIZED.name()).build());
    }
}
