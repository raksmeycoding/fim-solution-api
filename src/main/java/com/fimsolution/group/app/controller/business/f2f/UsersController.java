package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserReqDto;
import com.fimsolution.group.app.dto.business.f2f.user.UserResDto;
import com.fimsolution.group.app.mapper.business.f2f.UsersMapper;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.service.f2f.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/f2f")
public class UsersController {

    @Qualifier("UsersServiceImpl")
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<RespondDto<UserResDto>> createUser(@Valid @RequestBody RequestDto<UserReqDto> usersRequestRequestDto) {
        return ResponseEntity.ok().body(RespondDto.<UserResDto>builder()
                .data(userService.createUser(usersRequestRequestDto.getRequest()))
                .message("User is created.").build());
    }


    @DeleteMapping("users/{id}")
    public ResponseEntity<GenericDto<?>> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(GenericDto.builder().message("User is deleted.").code("200").build());
    }


    @GetMapping("/users/all")
    public ResponseEntity<RespondDto<List<UserResDto>>> getAllUsers() {
        return ResponseEntity.ok()
                .body(RespondDto.<List<UserResDto>>builder()
                        .data(userService.getAllUsers())
                        .httpStatusCode(HttpStatus.OK.value())
                        .httpStatusName(HttpStatus.OK)
                        .message("Get all users.")
                        .build());
    }

}
