package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.UsersDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserReqDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserResDto;
import com.fimsolution.group.app.model.business.f2f.User;

import java.util.List;

public interface UserService {
    UserResDto createUser(UserReqDto userReqDto);
    void deleteUserById(String id);

    List<UserResDto> getAllUsers();

}
