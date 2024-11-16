package com.fimsolution.group.app.controller;


import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsersTestController {


    private final UsersService usersService;


    @PostMapping("/users")
    public ResponseEntity<?> creatUser(@RequestBody User user) {
        return ResponseEntity.ok(usersService.createUser(user));
    }
}
