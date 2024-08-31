package com.fimsolution.group.app.service.seviceImpl;


import com.fimsolution.group.app.constant.ResponseStatus;
import com.fimsolution.group.app.dto.GenericDto;

import com.fimsolution.group.app.exception.AlreadyExistException;
import com.fimsolution.group.app.model.security.Role;
import com.fimsolution.group.app.model.security.UserCredentials;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.AppUserRepository;

import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.dto.auth.UserLoginRequest;
import com.fimsolution.group.app.repository.RefreshTokenRepository;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.security.JwtServiceImpl;
import com.fimsolution.group.app.security.LogoutServiceHandler;
import com.fimsolution.group.app.security.RefreshTokenServiceImpl;
import com.fimsolution.group.app.service.AuthenticationService;
import com.fimsolution.group.app.service.RoleService;
import com.fimsolution.group.app.utils.ProfileHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final RoleService roleServiceImpl;
    private final UserCredentialRepository userCredentialRepository;
    private final LogoutServiceHandler logoutServiceHandler;

    @Value("${jwt.cookieAccessExpiration}")
    private int jwtCookieAccessExpiration;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final ProfileHelper profileHelper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public GenericDto<String> register(GenericDto<RegisterRequest> requestDto) {
        logger.info("request body:{}", requestDto);

        RegisterRequest request = requestDto.getData();

        // User already existing validation
        Optional<UserCredentials> foundUser = userCredentialRepository.findByUsername(request.getEmail());

        foundUser.ifPresent(user -> {
            throw new AlreadyExistException("User already exists in our system");
        });

        UserCredentials userCredentials = UserCredentials.builder()
                .userId(null)
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstName())
                .lastname(request.getFirstName())
                .phone(request.getPhone())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        // find role or create default role
        Role role = roleServiceImpl.getOrCreateDefaultRole();

        userCredentials.setRoles(Set.of(role));

        UserCredentials userCredential = userCredentialRepository.save(userCredentials);


//        String username = user.getUsername();
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
////        response.getWriter().write("Login successful");
//        response.setStatus(HttpServletResponse.SC_OK);


        return new GenericDto<>(ResponseStatus.CREATED);

    }

    @Override
    public GenericDto<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        logoutServiceHandler.logout(request, response, authentication);
        return new GenericDto<>(ResponseStatus.SUCCESS);
    }

    @Override
    public GenericDto<String> login(GenericDto<UserLoginRequest> requestDto, HttpServletResponse response) {
        UserLoginRequest userLoginRequest = requestDto.getData();
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        UserCredentials userCredentials = userCredentialRepository.findByUsername(email).orElseThrow(() -> new NotFoundException("User has not been found"));

        boolean matchedPassword = passwordEncoder.matches(password, userCredentials.getPassword());
        if (!matchedPassword) {
            throw new RuntimeException("Password is not matched!");
        }

        UserDetailsImpl userDetails = UserDetailsImpl.builder().username(email).build();
        String accessToken = jwtServiceImpl.generateToken(userDetails);

        ResponseCookie responseCookie = ResponseCookie.fromClientResponse("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtCookieAccessExpiration)
                .build();


        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

//        response.getWriter().write("Login successful");
        response.setStatus(HttpServletResponse.SC_OK);

        return new GenericDto<>(ResponseStatus.CREATED);
    }


    @Override
    public GenericDto<?> refreshToken(HttpServletRequest request) {

//        String refreshToken = jwtServiceImpl.getJwtRefreshFromCookie(request);
//
//        if (refreshToken != null && !refreshToken.isEmpty()) {
//            return refreshTokenRepository.findByToken(refreshToken)
//                    .map(refreshTokenService::verifyRefreshToken)
//                    .map(RefreshToken::getUserCredentials)
//                    .map(userCredentials -> {
//                        ResponseCookie jwtCookie = jwtServiceImpl.generateJwtCookie(UserDetailsImpl.build(userCredentials));
//                        return GenericDto<String>
//                        return ResponseEntity.ok()
//                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                                .body("Token is refreshed successfully!");
//                    })
//        }


        return null;
    }
}
