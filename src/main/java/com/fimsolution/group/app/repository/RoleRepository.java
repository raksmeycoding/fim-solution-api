package com.fimsolution.group.app.repository;

import com.fimsolution.group.app.model.security.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Custom query method to find a Role by its name
    Optional<Role> findByName(String name);

}
