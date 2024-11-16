package com.fimsolution.group.app.service.seviceImpl;


import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import com.fimsolution.group.app.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("userServiceTestImpl")
@RequiredArgsConstructor
public class UsersServiceTestImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceTestImpl.class);
    private final UsersRepository usersRepository;

    @Override
    public User createUser(User user) {
        logger.info(":::createUser:::");
        return usersRepository.save(user);
    }
}
