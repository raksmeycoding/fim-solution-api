package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.UsersDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserReqDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserResDto;
import com.fimsolution.group.app.exception.AlreadyExistException;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.UsersMapper;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("UsersServiceImpl")
public class UsersServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);


    private final UsersRepository usersRepository;
    private final UserCredentialRepository userCredentialRepository;

    public UsersServiceImpl(UsersRepository usersRepository, UserCredentialRepository userCredentialRepository) {
        this.usersRepository = usersRepository;
        this.userCredentialRepository = userCredentialRepository;
    }


    @Override
    @Transactional
    public UserResDto createUser(UserReqDto userReqDto) {
        // TODO: check if user is already existed or not in the security user
        // NOTE: create user
        // TODO:

        usersRepository.findByEmail(userReqDto.getEmail())
                .ifPresent(user ->
                {
                    throw new AlreadyExistException("User has already in system");
                });

        Optional<UserCredential> findUserCredentials = userCredentialRepository.findByUsername(userReqDto.getEmail());

        if(findUserCredentials.isPresent()) {
            User user = UsersMapper.toEntity(userReqDto);
            user.setUserCredential(findUserCredentials.get());
            return UsersMapper.toResDto(usersRepository.save(user));
        }

        User user = UsersMapper.toEntity(userReqDto);
        return UsersMapper.toResDto(usersRepository.save(user));



    }

    @Override
    public void deleteUserById(String id) {
        usersRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User is not found")
                );

        usersRepository.deleteById(id);
    }


    @Override
    public List<UserResDto> getAllUsers() {
        return usersRepository.findAll()
                .stream()
                .map(UsersMapper::toResDto)
                .toList();
    }
}
