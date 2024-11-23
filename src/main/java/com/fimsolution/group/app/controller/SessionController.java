package com.fimsolution.group.app.controller;


import com.fimsolution.group.app.dto.RespondDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verify")
public class SessionController {


    @PostMapping("/session")
    public ResponseEntity<RespondDto<Map<String, String>>> verifyUserSession(HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
