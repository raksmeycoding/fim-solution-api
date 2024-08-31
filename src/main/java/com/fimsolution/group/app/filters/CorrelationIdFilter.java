package com.fimsolution.group.app.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Request-ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestId = getCorrelationIdFromHeader(request);

        // Set CorrelationId ID in MDC
        MDC.put(CORRELATION_ID_HEADER, requestId);


        // Log the incoming request details
        logRequestDetails(request, requestId);

        // Add correlation ID to the response header
        response.setHeader(CORRELATION_ID_HEADER, requestId);


        try {
            filterChain.doFilter(request, response);
        } finally {
            // Clean up MDC after the request is processed
            MDC.clear();
        }

    }


    private String getCorrelationIdFromHeader(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-ID");
        return (requestId == null || requestId.isEmpty()) ? UUID.randomUUID().toString().replace("-", "") : requestId;
    }

    private void logRequestDetails(HttpServletRequest request, String requestId) {
        LOGGER.info("Incoming Request: method={}, uri={}, requestId={}", request.getMethod(), request.getRequestURI(), requestId);
    }
}
