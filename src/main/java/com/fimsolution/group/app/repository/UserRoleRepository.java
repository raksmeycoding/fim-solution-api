package com.fimsolution.group.app.repository;

import com.fimsolution.group.app.model.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

}
