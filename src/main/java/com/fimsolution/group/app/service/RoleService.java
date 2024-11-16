package com.fimsolution.group.app.service;

import com.fimsolution.group.app.model.security.Role;

public interface RoleService {
    Role getOrCreateDefaultRole();


//    void assignRoleToUser(UserCredentials userCredentials, Role role);
}
