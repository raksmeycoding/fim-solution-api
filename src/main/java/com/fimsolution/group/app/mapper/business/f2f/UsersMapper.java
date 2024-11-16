package com.fimsolution.group.app.mapper.business.f2f;

import com.fimsolution.group.app.dto.business.f2f.UsersDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserReqDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserResDto;
import com.fimsolution.group.app.model.business.f2f.User;

public class UsersMapper {

    // Convert User entity to UserDto
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .givenName(user.getGivenName())
                .middleName(user.getMiddleName())
                .familyName(user.getFamilyName())
                .nickName(user.getNickName())
                .build();
    }

    // Convert UserReqDto to User entity
    public static User toEntity(UserReqDto userReqDto) {
        if (userReqDto == null) {
            return null;
        }
        return User.builder()
                .email(userReqDto.getEmail())
                .givenName(userReqDto.getGivenName())
                .middleName(userReqDto.getMiddleName())
                .familyName(userReqDto.getFamilyName())
                .nickName(userReqDto.getNickName())
                .build();
    }

    // Convert User entity to UserResDto
    public static UserResDto toResDto(User user) {
        if (user == null) {
            return null;
        }
        return UserResDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .givenName(user.getGivenName())
                .middleName(user.getMiddleName())
                .familyName(user.getFamilyName())
                .nickName(user.getNickName())
                .build();
    }

    // Convert User entity to UserReqDto
    public static UserReqDto toReqDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserReqDto(
                user.getEmail(),
                user.getGivenName(),
                user.getMiddleName(),
                user.getFamilyName(),
                user.getNickName()
        );
    }

    // Convert UserResDto to User entity
    public static User fromResDto(UserResDto userResDto) {
        if (userResDto == null) {
            return null;
        }
        return User.builder()
                .id(userResDto.getUserId()) // assuming the userId corresponds to the entity's id
                .givenName(userResDto.getGivenName())
                .email(userResDto.getEmail())
                .middleName(userResDto.getMiddleName())
                .familyName(userResDto.getFamilyName())
                .nickName(userResDto.getNickName())
                .build();
    }


}
