package com.fimsolution.group.app.security;


import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private final UserCredentialRepository userCredentialRepository;
    private final LoanUsersRepository loanUsersRepository;


    public UserDetailsServiceImpl(UserCredentialRepository userCredentialRepository, LoanUsersRepository loanUsersRepository) {
        this.userCredentialRepository = userCredentialRepository;
        this.loanUsersRepository = loanUsersRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        UserCredential user = userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User has not been found"));
        logger.info("User Authenticated Successfully");
        logger.info("UserCredentials: {}", user);
        return UserDetailsImpl.buildUserCredentials(user);
    }
}
