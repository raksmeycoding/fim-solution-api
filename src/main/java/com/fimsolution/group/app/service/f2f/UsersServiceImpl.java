package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.UsersDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserReqDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserResDto;
import com.fimsolution.group.app.exception.AlreadyExistException;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.UsersMapper;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("UsersServiceImpl")
public class UsersServiceImpl implements UserService {


    private final UsersRepository usersRepository;
    private final UserCredentialRepository userCredentialRepository;

    public UsersServiceImpl(UsersRepository usersRepository, UserCredentialRepository userCredentialRepository) {
        this.usersRepository = usersRepository;
        this.userCredentialRepository = userCredentialRepository;
    }


    @Override
    public UserResDto createUser(UserReqDto userReqDto) {
        // TODO: check if user is already existed or not in the security user
        // NOTE: create user
        // TODO:

        usersRepository.findByEmail(userReqDto.getEmail())
                .ifPresent(user ->
                {
                    throw new AlreadyExistException("User has already in system - or user already registered before admin created");
                });

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
