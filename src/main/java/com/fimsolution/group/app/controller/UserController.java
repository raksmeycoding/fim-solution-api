package com.fimsolution.group.app.controller;


import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.business.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @GetMapping("/noAuthUserProfile")
    public ResponseEntity<GenericDto<UserProfile>> getNoAuthUserProfile() {
        return ResponseEntity.ok(GenericDto.<UserProfile>builder()
                .data(UserProfile.builder()
                        .id(UUID.randomUUID().toString())
                        .username("David Kelvin").build())
                .build());
    }
}
