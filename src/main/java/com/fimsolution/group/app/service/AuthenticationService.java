package com.fimsolution.group.app.service;

import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.dto.auth.SessionDto;
import com.fimsolution.group.app.dto.auth.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;


public interface AuthenticationService {
    GenericDto<String> login(GenericDto<UserLoginRequest> requestDto, HttpServletResponse httpServletResponse);

    RespondDto<String> register(RequestDto<RegisterRequest> requestDto);

    GenericDto<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    GenericDto<?> refreshToken(HttpServletRequest request);

    SessionDto verifyUserRoleBasedAccess();


}
