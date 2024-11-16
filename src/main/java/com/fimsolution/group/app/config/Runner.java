package com.fimsolution.group.app.config;

import com.fimsolution.group.app.controller.AuthenticationController;
import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.auth.RegisterRequest;
import com.fimsolution.group.app.model.security.Role;
import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.model.security.UserRole;
import com.fimsolution.group.app.repository.RoleRepository;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.UserRoleRepository;
import com.fimsolution.group.app.utils.FimSendingMailOtp;
import com.fimsolution.group.app.utils.ProfileHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
@Profile("dev")
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final ProfileHelper profileHelper;
    private final FimSendingMailOtp fimSendingMailOtp;
    private final static Logger logger = LoggerFactory.getLogger(Runner.class);
    private final RoleRepository roleRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationController authenticationController;


    @Override
    public void run(String... args) throws Exception {

//        fimSendingMailOtp.sendingOtp(fimSendingMailOtp.createDefaultBodyBuilder());
        logger.info("Saving Role:::");
        Role role1 = roleRepository.save(Role.builder().name("ROLE_USER").build());
        Role role2 = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        Role role3 = roleRepository.save(Role.builder().name("ROLE_PROXY").build());
        Role role4 = roleRepository.save(Role.builder().name("ROLE_LOAN_USER").build());
        logger.info("Saving Role1 Success:::{}", role1);
        logger.info("Saving Role2 Success:::{}", role2);
        logger.info("Saving Role3 Success:::{}", role3);
        logger.info("Saving Role3 Success:::{}", role4);

        String passwordAdmin = passwordEncoder.encode("2H5pLJdKIzGdrt.&afYy");
        UserCredential userCredential = UserCredential.builder()
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .lastname("Admin")
                .firstname("Admin")
                .phone("8557876874")
                .username("admin@gmail.com")
                .password(passwordAdmin)
                .build();


//
//
        UserRole userRole = UserRole.builder()
                .role(role2)
                .userCredential(userCredential)
                .build();

        userCredential.setUserRole(Set.of(userRole));


        userCredentialRepository.save(userCredential);
//


    }


}
