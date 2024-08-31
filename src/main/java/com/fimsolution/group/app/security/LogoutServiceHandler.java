package com.fimsolution.group.app.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LogoutServiceHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("accessToken".equals(c.getName())) {
                    c.setValue(null);
                    c.setPath("/");
                    c.setMaxAge(0);
                    c.setHttpOnly(true);
                    c.setSecure(true);
                    response.addCookie(c);

                }
            }


        }

        SecurityContextHolder.clearContext();


    }
}
