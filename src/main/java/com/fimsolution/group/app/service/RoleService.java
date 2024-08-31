package com.fimsolution.group.app.service;

import com.fimsolution.group.app.model.security.Role;
import com.fimsolution.group.app.model.security.UserCredentials;

public interface RoleService {
    Role getOrCreateDefaultRole();


//    void assignRoleToUser(UserCredentials userCredentials, Role role);
}
