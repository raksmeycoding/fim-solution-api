package com.fimsolution.group.app.security;


import com.fimsolution.group.app.model.security.UserCredentials;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private final UserCredentialRepository userCredentialRepository;

    public UserDetailsServiceImpl(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        UserCredentials user = userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User has not been found"));
        logger.info("User Authenticated Successfully");
        logger.info("UserCredentials: {}", user);
        return UserDetailsImpl.buildUserCredentials(user);
    }
}
