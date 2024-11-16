package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.model.business.f2f.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
