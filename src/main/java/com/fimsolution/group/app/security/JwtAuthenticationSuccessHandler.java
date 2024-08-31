//package com.fimsolution.group.app.security;
//
//import com.fimsolution.group.app.model.security.UserDetailsImpl;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseCookie;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//
//@Component
//public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private JwtService jwtServiceImpl;
//    @Value("${jwt.cookieExpiry}")
//    private int cookieExpiry;
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String username = authentication.getName();
//        UserDetailsImpl userDetails = new UserDetailsImpl(null, username, null, null);
//        String accessToken = jwtServiceImpl.generateToken(userDetails);
//
//        ResponseCookie responseCookie = ResponseCookie.fromClientResponse("accessToken", accessToken)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(cookieExpiry)
//                .build();
//
//
//        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
//
//        response.getWriter().write("Login successful");
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//}
