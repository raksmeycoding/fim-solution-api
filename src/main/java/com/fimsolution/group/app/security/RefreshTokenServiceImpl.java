package com.fimsolution.group.app.security;


import com.fimsolution.group.app.model.security.RefreshToken;
import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.repository.RefreshTokenRepository;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.cookieRefreshExpiration}")
    private int jwtCookieRefreshExpiration;
    private final static Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserCredentialRepository userCredentialRepository;


    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }


    @Transactional
    public RefreshToken createRefreshToken(String username) {
        RefreshToken newRefreshToken = new RefreshToken();

        UserCredential userCredential = userCredentialRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User has not been found"));


        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUserCredentialId(userCredential.getId());


        refreshTokenOptional.ifPresent(refreshToken -> refreshTokenRepository.deleteByToken(refreshToken.getToken()));


//        newRefreshToken.setExpiryDate(Instant.now().plusMillis(jwtCookieRefreshExpiration));
        newRefreshToken.setToken(UUID.randomUUID().toString());
        newRefreshToken.setUserCredential(userCredential);

        return refreshTokenRepository.save(newRefreshToken);
    }


    @Transactional
    public RefreshToken verifyRefreshToken(RefreshToken refreshToken) {
//        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(refreshToken);
//            throw new RuntimeException(refreshToken.getToken() + " has been expired. Please make a new sing in request!");
//        }
        return refreshToken;
    }


    @Transactional
    public void deleteByUserId(String userCredentialId) {
        refreshTokenRepository
                .deleteByUserCredential(userCredentialRepository.findById(userCredentialId)
                        .orElseThrow(() -> new RuntimeException("Unable to delete refresh token by userCredential")));
    }
}
