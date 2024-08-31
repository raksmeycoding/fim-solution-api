package com.fimsolution.group.app.service;

import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.dto.auth.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;


public interface AuthenticationService {
    GenericDto<String> login(GenericDto<UserLoginRequest> requestDto, HttpServletResponse httpServletResponse);
    GenericDto<String> register(GenericDto<RegisterRequest> requestDto)  ;

    GenericDto<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
    GenericDto<?> refreshToken(HttpServletRequest request);


}
