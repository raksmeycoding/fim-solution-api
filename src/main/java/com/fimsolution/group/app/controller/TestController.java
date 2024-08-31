package com.fimsolution.group.app.controller;


import com.fimsolution.group.app.dto.GenericDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public ResponseEntity<GenericDto<String>> hello() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Hello Controller::{}", authentication);

        return ResponseEntity.ok().body(GenericDto.<String>builder().code("200").message("Saying hello from SpringBoot!").build());
    }
}
