package com.fimsolution.group.app.controller;

import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.dto.auth.ResponseUserInfo;
import com.fimsolution.group.app.dto.auth.RoleResDto;
import com.fimsolution.group.app.dto.auth.UserLoginRequest;
import com.fimsolution.group.app.dto.business.f2f.user.UserResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.model.security.RefreshToken;
import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.RefreshTokenRepository;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.security.JwtServiceImpl;
import com.fimsolution.group.app.security.RefreshTokenServiceImpl;
import com.fimsolution.group.app.service.AuthenticationService;
import com.fimsolution.group.app.utils.CookieUtils;
import com.fimsolution.group.app.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationServiceImpl;
    private final RefreshTokenServiceImpl refreshTokenServiceImpl;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final LoanUsersRepository loanUsersRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final Environment environment;
    private final JwtUtils jwtUtils;
    @PersistenceContext
    private EntityManager entityManager;

    private final HttpServletResponse httpServletResponse;

    @Value("${jwt.cookieName}")
    private String jwtCookieName;

    @Value("${jwt.refreshCookieName}")
    private String jwtRefreshCookieName;
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "registration endpoint.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation Completed Successfully")
            }
    )
    public ResponseEntity<RespondDto<String>> register(@Valid @RequestBody RequestDto<RegisterRequest> requestDto) {

//        logger.info(":::Log Security Object:::{}", authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationServiceImpl.register(requestDto));
    }


    @Operation(
            summary = "User login",
            description = "User login endpoint",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation Completed Successfully")
            }
    )
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<RespondDto<ResponseUserInfo>> login(@RequestBody @Valid RequestDto<UserLoginRequest> request) {
        UserLoginRequest userLoginRequest = request.getRequest();


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

        Optional<UserCredential> credentialOptional = userCredentialRepository.findByUsername(userDetailsImpl.getUsername());

        if (credentialOptional.isEmpty())
            throw new NotFoundException("No login user not found");

        String accessToken = jwtUtils.generateJwtToken(userDetailsImpl);
        String refreshToken = jwtUtils.generateRefreshToken(userDetailsImpl);

//        ResponseCookie jwtCookie = jwtServiceImpl.genJwtCookie(userDetailsImpl);

        ResponseCookie jwtCookies = ResponseCookie.fromClientResponse(jwtRefreshCookieName, refreshToken)
                .path("/")
//                .maxAge(jwtCookieAccessExpiration)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();


        List<String> roles = userDetailsImpl.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        UserCredential userCredential = credentialOptional.get();

        RefreshToken refreshTokenObject = new RefreshToken();

        refreshTokenObject.setToken(refreshToken);

        refreshTokenObject.setUserCredential(userCredential);

        userCredential.setRefreshToken(refreshTokenObject);

        userCredential = userCredentialRepository.save(userCredential);


        ResponseCookie jwtResKey = ResponseCookie
                .fromClientResponse("__re_key", userCredential.getRefreshToken().getId())
                .path("/")
                .build();


        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.SET_COOKIE, jwtCookies.toString());
                    httpHeaders.add(HttpHeaders.SET_COOKIE, jwtResKey.toString());
                })
                .body(RespondDto.<ResponseUserInfo>builder()
                        .httpStatusCode(HttpStatus.CREATED.value())
                        .httpStatusName(HttpStatus.CREATED)
                        .data(ResponseUserInfo.builder()
                                .email(userDetailsImpl.getUsername())
                                .token(accessToken)
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
        try {
            // 1. Clear Security Context
            SecurityContextHolder.clearContext();

            // 2. Read refresh token from cookies

            Optional<String> refreshTokenOptional = CookieUtils.readCookie("__fim_rf_id", httpServletRequest);
            Optional<String> refreshTokenIdOptional = CookieUtils.readCookie("__re_key", httpServletRequest);


            if (refreshTokenOptional.isEmpty() || refreshTokenIdOptional.isEmpty()) {
                // If no valid refresh token or refresh token ID in cookies, simply return an unauthorized status.
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            String refreshToken = refreshTokenOptional.get();
            String refreshTokenId = refreshTokenIdOptional.get();

            // 3. Invalidate refresh token if it's stored in a database
            Optional<RefreshToken> refreshTokenOptionalDb = refreshTokenRepository.findById(refreshTokenId);

            // You can either delete the refresh token from the database or mark it as invalid
            refreshTokenOptionalDb.ifPresent(refreshTokenRepository::delete);

            // 4. Clear cookies by setting them to expired (removing sensitive tokens)
            ResponseCookie clearRefreshTokenCookie = ResponseCookie.fromClientResponse("__fim_rf_id", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0) // This makes the cookie expire immediately
                    .build();

            ResponseCookie clearRefreshTokenIdCookie = ResponseCookie.fromClientResponse("__re_key", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0) // Expire the refresh token ID cookie as well
                    .build();


            // 5. Return success response
            return ResponseEntity.ok().headers(httpHeaders -> {
                httpHeaders.add(HttpHeaders.SET_COOKIE, clearRefreshTokenCookie.toString());
                httpHeaders.add(HttpHeaders.SET_COOKIE, clearRefreshTokenIdCookie.toString());
            }).build();
        } catch (Exception e) {
            logger.info(":::Log Security Object:::{}", e.getMessage());
            // 4. Clear cookies by setting them to expired (removing sensitive tokens)
            ResponseCookie clearRefreshTokenCookie = ResponseCookie.fromClientResponse("__fim_rf_id", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0) // This makes the cookie expire immediately
                    .build();

            ResponseCookie clearRefreshTokenIdCookie = ResponseCookie.fromClientResponse("__re_key", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0) // Expire the refresh token ID cookie as well
                    .build();
            return ResponseEntity.ok().headers(httpHeaders -> {
                httpHeaders.add(HttpHeaders.SET_COOKIE, clearRefreshTokenCookie.toString());
                httpHeaders.add(HttpHeaders.SET_COOKIE, clearRefreshTokenIdCookie.toString());
            }).build();
        }
    }


    @Operation(
            summary = "User refresh token",
            description = "User refresh endpoint",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation Completed Successfully")
            }
    )
    @PostMapping("/refreshToken")
    @Transactional
    public ResponseEntity<RespondDto<ResponseUserInfo>> refreshToken(HttpServletRequest httpServletRequest) {

        Optional<String> refreshCookieOptional = CookieUtils.readCookie("__fim_rf_id", httpServletRequest);
        Optional<String> cookieIdOptional = CookieUtils.readCookie("__re_key", httpServletRequest);

        if (refreshCookieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (cookieIdOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String refreshTokenCookies = refreshCookieOptional.get();
        String refreshTokenId = cookieIdOptional.get();

        // Fetch the refresh token from DB
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(refreshTokenId);
        if (refreshTokenOptional.isEmpty()) {

            // 4. Clear cookies by setting them to expired (removing sensitive tokens)
            ResponseCookie clearRefreshTokenCookie = ResponseCookie.fromClientResponse("__fim_rf_id", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0) // This makes the cookie expire immediately
                    .build();

            ResponseCookie clearRefreshTokenIdCookie = ResponseCookie.fromClientResponse("__re_key", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0) // Expire the refresh token ID cookie as well
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders -> {
                        httpHeaders.add(HttpHeaders.SET_COOKIE, clearRefreshTokenCookie.toString());
                        httpHeaders.add(HttpHeaders.SET_COOKIE, clearRefreshTokenIdCookie.toString());
                    })
                    .build();
        }

        RefreshToken refreshTokenFromDb = refreshTokenOptional.get();
        UserCredential userCredentialFromDb = refreshTokenFromDb.getUserCredential();

        // Remove the refresh token (set null), persist changes
//        userCredentialFromDb.setRefreshToken(null);
        UserCredential savedUserCredential = userCredentialRepository.save(userCredentialFromDb); // Save user credential after token removal


        // Generate new access token and refresh token
        UserDetailsImpl userDetails = UserDetailsImpl.buildUserCredentials(savedUserCredential);
        String genAccessToken = jwtUtils.generateJwtToken(userDetails);
        String genRefreshToken = jwtUtils.generateRefreshToken(userDetails);

        // Create a new RefreshToken entity
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken(genRefreshToken);
        newRefreshToken.setUserCredential(userCredentialFromDb);

        // Associate the new refresh token with the user credential and save
        savedUserCredential.setRefreshToken(newRefreshToken);
        UserCredential userDetailsWithNewRefresh = userCredentialRepository.save(savedUserCredential); // Save updated user credential with new token

        // Create cookies for response
        RefreshToken savedRefreshToken = userDetailsWithNewRefresh.getRefreshToken();

        ResponseCookie refreshTokenCookiesWithId = ResponseCookie.fromClientResponse("__re_key", savedRefreshToken.getId())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .build();

        ResponseCookie refreshCookies = ResponseCookie.fromClientResponse("__fim_rf_id", genRefreshToken)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .secure(true)
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookiesWithId.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookies.toString())
                .body(RespondDto.<ResponseUserInfo>builder()
                        .data(ResponseUserInfo.builder()
                                .email(savedUserCredential.getUsername())
                                .token(genAccessToken)
                                .build())
                        .build());
    }


}
