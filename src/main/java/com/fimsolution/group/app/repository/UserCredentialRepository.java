package com.fimsolution.group.app.repository;

import com.fimsolution.group.app.model.security.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials, UUID> {
    Optional<UserCredentials> findByUsername(String username);

}
