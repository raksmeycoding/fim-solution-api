package com.fimsolution.group.app.service.seviceImpl;


import com.fimsolution.group.app.constant.ResponseStatus;
import com.fimsolution.group.app.dto.GenericDto;

import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.exception.AlreadyExistException;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.model.security.*;
import com.fimsolution.group.app.repository.*;

import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.dto.auth.UserLoginRequest;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import com.fimsolution.group.app.security.JwtServiceImpl;
import com.fimsolution.group.app.security.LogoutServiceHandler;
import com.fimsolution.group.app.service.AuthenticationService;
import com.fimsolution.group.app.service.RoleService;
import com.fimsolution.group.app.utils.JwtUtils;
import com.fimsolution.group.app.utils.ProfileHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final RoleService roleServiceImpl;
    private final UserCredentialRepository userCredentialRepository;
    private final LogoutServiceHandler logoutServiceHandler;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.cookieAccessExpiration}")
    private int jwtCookieAccessExpiration;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final ProfileHelper profileHelper;

    @Override
    @Transactional
    public RespondDto<String> register(RequestDto<RegisterRequest> requestDto) {
        logger.info("request body:{}", requestDto);

        RegisterRequest request = requestDto.getRequest();

        // User already existing validation
        Optional<UserCredential> foundUser = userCredentialRepository.findByUsername(request.getEmail());

        foundUser.ifPresent(user -> {
            throw new AlreadyExistException("User already exists in our system");
        });

        UserCredential userCredential = UserCredential.builder()
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

        // Save registered user to db first
        UserCredential registerUser = userCredentialRepository.save(userCredential);


        Optional<User> optionalPreFindUser = usersRepository.findByEmail(request.getEmail());


        if (optionalPreFindUser.isPresent()) {

            User newUser = optionalPreFindUser.get();

            newUser.setUserCredential(registerUser);

            usersRepository.save(newUser);

        } else {

            // Save User repository
            User user = User.builder().familyName("DEFAULT").givenName("DEFAULT").middleName("DEFAULT").nickName("DEFAULT").email(userCredential.getUsername()).build();
            user.setUserCredential(userCredential);
            user = usersRepository.save(user);
        }


        // Find Role
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");

        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role not found");
        }


        UserRole userRole = UserRole.builder()
                .role(optionalRole.get())
                .userCredential(registerUser)
                .build();


        userRoleRepository.save(userRole);

        // ToDo: Sending OPT to user


        return RespondDto.<String>builder().httpStatusName(HttpStatus.CREATED).httpStatusCode(HttpStatus.CREATED.value()).data("Register successfully").build();

    }


    @Override
    public GenericDto<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        logoutServiceHandler.logout(request, response, authentication);
        return new GenericDto<>(ResponseStatus.SUCCESS);
    }

    @Override
    @Transactional
    public GenericDto<String> login(GenericDto<UserLoginRequest> requestDto, HttpServletResponse response) {
        UserLoginRequest userLoginRequest = requestDto.getData();
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        UserCredential userCredential = userCredentialRepository.findByUsername(email).orElseThrow(() -> new NotFoundException("User has not been found"));

        boolean matchedPassword = passwordEncoder.matches(password, userCredential.getPassword());
        if (!matchedPassword) {
            throw new RuntimeException("Password is not matched!");
        }

        UserDetailsImpl userDetails = UserDetailsImpl.builder().username(email).build();
//        String accessToken = jwtServiceImpl.generateToken(userDetails);
//        String accessToken = jwtUtils.generateJwtToken();

//        Save to db
//        String refreshToken = jwtUtils.generateJwtToken();


        RefreshToken newRefreshToken = new RefreshToken();

        if (userCredential.getRefreshToken() == null) {
            newRefreshToken.setUserCredential(userCredential);
//            newRefreshToken.setToken(refreshToken);
            newRefreshToken = refreshTokenRepository.save(newRefreshToken);
        }


//        ResponseCookie responseCookie = ResponseCookie.fromClientResponse("accessToken", accessToken)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(jwtCookieAccessExpiration)
//                .build();


//        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

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
