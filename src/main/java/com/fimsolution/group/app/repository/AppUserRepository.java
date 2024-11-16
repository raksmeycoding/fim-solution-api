package com.fimsolution.group.app.repository;


import com.fimsolution.group.app.model.security.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<UserCredential, UUID> {

    Optional<UserCredential> findByUsername(String username);
}
