package com.fimsolution.group.app.service.seviceImpl;

import com.fimsolution.group.app.model.security.Role;
import com.fimsolution.group.app.repository.RoleRepository;
import com.fimsolution.group.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.fimsolution.group.app.constant.ConstantUserRole.ROLE_USER;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Role getOrCreateDefaultRole() {
        return roleRepository.findByName(ROLE_USER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(ROLE_USER).build()));
    }

}
