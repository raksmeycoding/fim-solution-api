package com.fimsolution.group.app.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    public static Optional<String> findJwtFimRid(HttpServletRequest httpServletRequest) {
        // Use Stream to find the "jwt __fim_rf_id" cookie
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> "__fim_rf_id" .equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
