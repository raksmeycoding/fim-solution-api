package com.fimsolution.group.app.repository;

import com.fimsolution.group.app.model.security.RefreshToken;
import com.fimsolution.group.app.model.security.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);


    Optional<RefreshToken> deleteByToken(String token);


    void deleteByUserCredentials(UserCredentials userCredentials);

    Optional<RefreshToken> findByUserCredentials(UserCredentials userCredentials);

}
