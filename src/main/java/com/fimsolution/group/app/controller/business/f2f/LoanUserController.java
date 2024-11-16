package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LinkLoanUserToUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;
import com.fimsolution.group.app.service.f2f.LoanUsersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/f2f")
@RequiredArgsConstructor
public class LoanUserController {

    private final LoanUsersService loanUsersService;
    private final Logger logger = LoggerFactory.getLogger(LoanUserController.class);

    @PostMapping("/loan-user")
    public ResponseEntity<RespondDto<LoanUserResDto>> createLoanUser(@RequestBody RequestDto<LoanUserReqDto> loanUserReqDtoRequestDto) {

        logger.info("createLoanUser:::{}", loanUserReqDtoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(RespondDto.<LoanUserResDto>builder()
                .data(loanUsersService.createLoanUser(loanUserReqDtoRequestDto.getRequest()))
                .httpStatusCode(HttpStatus.CREATED.value())
                .httpStatusName(HttpStatus.CREATED)
                .message("Loan Users Created")
                .build());
    }


    @GetMapping("/loan-users")
    public ResponseEntity<RespondDto<List<LoanUserProjection>>> getAllLoans() {

        return ResponseEntity.status(HttpStatus.CREATED).body(RespondDto.<List<LoanUserProjection>>builder()
                .data(loanUsersService.getLoanUsers())
                .httpStatusCode(HttpStatus.CREATED.value())
                .httpStatusName(HttpStatus.CREATED)
                .message("Loan Users Created")
                .build());
    }

    @PostMapping("/loan-user/link")
    public ResponseEntity<RespondDto<Boolean>> linkLoanUsersToUser(@RequestBody RequestDto<LinkLoanUserToUserReqDto> requestDto) {
        return ResponseEntity.ok().body(RespondDto.<Boolean>builder()
                .message("Link success")
                .httpStatusName(HttpStatus.OK)
                .httpStatusCode(HttpStatus.OK.value())
                .data(loanUsersService.linkLonUserToUser(requestDto.getRequest()))
                .build());
    }


    /**
     * @TODO: creat get all loan users if have times to foster development.
     * */

}
