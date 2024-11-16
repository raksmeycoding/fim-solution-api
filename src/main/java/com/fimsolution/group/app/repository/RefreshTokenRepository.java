package com.fimsolution.group.app.repository;

import com.fimsolution.group.app.model.security.RefreshToken;
import com.fimsolution.group.app.model.security.UserCredential;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);


    Optional<RefreshToken> deleteByToken(String token);


    void deleteByUserCredential(UserCredential userCredential);

    Optional<RefreshToken> findByUserCredentialId(String id);

}
