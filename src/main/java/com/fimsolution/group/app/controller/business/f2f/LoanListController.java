package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.loanList.LoanListResDto;
import com.fimsolution.group.app.service.f2f.LoanListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/f2f")
@RequiredArgsConstructor
public class LoanListController {

    private final LoanListService loanListService;


    @GetMapping("/loan-list")
    public ResponseEntity<RespondDto<LoanListResDto>> getLoanListForCurrentUser() {
        return ResponseEntity.ok().body(RespondDto.<LoanListResDto>builder()
                        .data(loanListService.getLoanListsForCurrentUser())
                .build());
    }
}
