package com.fimsolution.group.app.controller;


import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.auth.SessionDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.service.AuthenticationService;
import com.fimsolution.group.app.service.f2f.LoanUsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verify")
public class SessionController {

    private final LoanUsersService loanUsersService;
    private final AuthenticationService authenticationService;


    @PostMapping("/session")
    public ResponseEntity<RespondDto<Map<String, String>>> verifyUserSession(HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/default/loan/exist")
    public ResponseEntity<RespondDto<Object>> checkUserHaveDefaultOrNot() {
        Optional<LoanUserProjection> loanUserProjection = loanUsersService.checkLoanUserHasDefaultLoan();
        return loanUserProjection
                .map(loanUserProjection1 -> ResponseEntity.ok().body(RespondDto.builder().data(loanUserProjection1).build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespondDto.builder().data(null).httpStatusName(HttpStatus.NOT_FOUND).httpStatusCode(404).errorMessage("Default loan user has not been found").build()));
    }


    @GetMapping("/session/verify/users")
    public ResponseEntity<?> hasRoleAdmin() {
        SessionDto sessionDto = authenticationService.verifyUserRoleBasedAccess();
        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.builder().data(sessionDto).httpStatusCode(200).build());
    }
}
