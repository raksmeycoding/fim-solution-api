package com.fimsolution.group.app.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    public static Optional<String> findJwtFimRid(HttpServletRequest httpServletRequest) {
        // Use Stream to find the "jwt __fim_rf_id" cookie
        try {
            return Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> "__fim_rf_id".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst();
        } catch (Exception e) {
            logger.error("Failed to read cookie");
            return Optional.empty();
        }
    }


    public static Optional<String> readCookie(String key, HttpServletRequest request) {
        try {
            return Arrays.stream(request.getCookies())
                    .filter(c -> key.equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny();
        } catch (Exception e) {
            logger.error("Failed to read cookie");
            return Optional.empty();
        }
    }

}
