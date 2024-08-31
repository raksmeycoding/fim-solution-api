package com.fimsolution.group.app.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

public class FilterHelper {

    public static List<String> url = List.of(
            "/actuator",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui",
            "/webjars",
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refreshToken",
            "/api/v1/auth/logout",
            "/api/v1/user/noAuthUserProfile"
    );

    public static boolean filterUrlContains(HttpServletRequest httpServletRequest) {
        return url.stream().anyMatch(httpServletRequest.getRequestURI()::startsWith);
    }
}
