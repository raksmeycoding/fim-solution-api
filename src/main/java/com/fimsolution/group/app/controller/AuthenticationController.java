package com.fimsolution.group.app.controller;

import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.dto.auth.ResponseUserInfo;
import com.fimsolution.group.app.dto.auth.UserLoginRequest;
import com.fimsolution.group.app.model.security.RefreshToken;
import com.fimsolution.group.app.model.security.UserCredentials;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.RefreshTokenRepository;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.security.JwtServiceImpl;
import com.fimsolution.group.app.security.RefreshTokenServiceImpl;
import com.fimsolution.group.app.service.AuthenticationService;
import com.fimsolution.group.app.utils.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationServiceImpl;
    private final RefreshTokenServiceImpl refreshTokenServiceImpl;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final UserCredentialRepository userCredentialRepository;
    private final Environment environment;
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "registration endpoint.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation Completed Successfully")
            }
    )
    public ResponseEntity<?> register(@Valid @RequestBody  GenericDto<RegisterRequest> requestDto) {

        return ResponseEntity.ok(authenticationServiceImpl.register(requestDto));
    }


    @Operation(
            summary = "User login",
            description = "User login endpoint",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation Completed Successfully")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody GenericDto<UserLoginRequest> request) {
        UserLoginRequest userLoginRequest = request.getData();


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtServiceImpl.genJwtCookie(userDetailsImpl);


        List<String> roles = userDetailsImpl.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenServiceImpl.createRefreshToken(userDetailsImpl.getUsername());


        ResponseCookie jwtRefreshToken = jwtServiceImpl.generateRefreshJwtCookie(refreshToken.getToken());


        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
                    httpHeaders.add(HttpHeaders.SET_COOKIE, jwtRefreshToken.toString());
                })
                .body(GenericDto.<ResponseUserInfo>builder()
                        .code("200")
                        .data(ResponseUserInfo.builder()
                                .email(userDetailsImpl.getUsername())
                                .roles(roles).build())
                        .message("Operation Completed Successfully")
                        .build());
    }


    @Operation(
            summary = "User logout",
            description = "User logout endpoint",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation Completed Successfully")
            }
    )
    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<?> logoutUser(HttpServletRequest httpServletRequest) {

        // Use Stream to find the "jwt __fim_rf_id" cookie
        Optional<String> jwtFimRfId = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> "__fim_rf_id" .equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();

        // Log the extracted cookie value or warn if not found
        jwtFimRfId.ifPresentOrElse(

                value -> {
                    logger.info("Extracted jwt __fim_rf_id cookie: {}", value);
                    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByToken(value);
                    optionalRefreshToken.ifPresent(refreshToken -> {
                        logger.info("found db refresh token:{}", refreshToken);
                        refreshTokenRepository.deleteById(refreshToken.getId());
                    });


                },

                () -> logger.warn("jwt __fim_rf_id cookie not found.")
        );


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !(authentication.getPrincipal() instanceof String
                && "anonymousUser" .equals(authentication.getPrincipal()))) {
            try {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                UUID userId = userDetails.getId();
                refreshTokenServiceImpl.deleteByUserId(userId);
                logger.info("Successfully deleted refresh tokens for user with ID: {}", userId);
            } catch (Exception e) {
                logger.error("Error occurred while deleting refresh tokens: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(GenericDto.<String>builder().message("Logout failed!").build());
            }
        }

        ResponseCookie jwtCookie = jwtServiceImpl.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtServiceImpl.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(GenericDto.<String>builder().message("You've been signed out!").build());
    }


    @Operation(
            summary = "User refresh token",
            description = "User refresh endpoint",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation Completed Successfully")
            }
    )
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest) {

        Optional<String> optionalRefreshToken = jwtServiceImpl.getJwtRefreshFromCookieOptional(httpServletRequest);

        if (optionalRefreshToken.isPresent()) {
            return refreshTokenServiceImpl.findByRefreshToken(optionalRefreshToken.get())
                    .map(refreshTokenServiceImpl::verifyRefreshToken)
                    .map(RefreshToken::getUserCredentials)
                    .map(userCredentials -> {

                        // jwt cookies
                        ResponseCookie responseCookie = jwtServiceImpl.generateJwtCookie(UserDetailsImpl.buildUserCredentials(userCredentials));

                        RefreshToken refreshToken = refreshTokenServiceImpl.createRefreshToken(userCredentials.getUsername());

                        ResponseCookie freshCookiesResponse = jwtServiceImpl.generateRefreshJwtCookie(refreshToken.getToken());


                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                                .header(HttpHeaders.SET_COOKIE, freshCookiesResponse.toString())
                                .body(GenericDto.builder().build());
                    })
                    .orElseThrow(() -> new RuntimeException("Refresh token is not found in our database"));
        }

        return ResponseEntity.badRequest().body(GenericDto.builder().message("Refresh Token must be not empty!").build());
    }


}
