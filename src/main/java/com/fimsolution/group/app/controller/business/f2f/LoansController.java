package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.LoanDto;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanReqDto;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanResDto;
import com.fimsolution.group.app.mapper.business.f2f.LoanMapper;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.service.f2f.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/f2f")
public class LoansController {

    private final LoanService loanService;

    public LoansController(LoanService loanService) {
        this.loanService = loanService;
    }


    @PostMapping("/loan")
    public ResponseEntity<RespondDto<LoanResDto>> createLoan(@RequestBody RequestDto<LoanReqDto> loanReqDtoRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespondDto.<LoanResDto>builder()
                        .data(loanService.createLoan(loanReqDtoRequestDto.getRequest()))
                        .httpStatusCode(HttpStatus.CREATED.value())
                        .message("loan is created").build());
    }

    @GetMapping("/loan/{id}")
    public ResponseEntity<RespondDto<?>> getLoanById(@PathVariable String id) {
        Loan loan = loanService.getLoanById(id);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RespondDto.builder().data(null).errorMessage("Loan not found").httpStatusCode(404).build());
        }
        LoanResDto loanResDto = LoanMapper.toResDto(loan);
        return ResponseEntity.ok(RespondDto.<LoanResDto>builder().httpStatusCode(200).message("Loan retrieved").data(loanResDto).build());
    }


    @PutMapping("/loan/{id}")
    public ResponseEntity<GenericDto<?>> updateLoan(@PathVariable String id,
                                                    @RequestBody RequestDto<LoanDto> loanRequestDto) {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericDto.builder().code("404").message("Loan not found").build());
        }
        Loan updatedLoan = LoanMapper.fromLoanDtoToEntity(loanRequestDto.getRequest());
        updatedLoan.setId(id);  // Preserve the existing ID
        loanService.updateLoan(updatedLoan);
        return ResponseEntity.ok(GenericDto.builder().code("200").message("Loan updated").build());
    }

    @DeleteMapping("/loan/{id}")
    public ResponseEntity<GenericDto<?>> deleteLoan(@PathVariable String id) {
        if (!loanService.deleteLoanById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericDto.builder().code("404").message("Loan not found").build());
        }
        return ResponseEntity.ok(GenericDto.builder().code("200").message("Loan deleted").build());
    }

    @GetMapping("/loans")
    public ResponseEntity<RespondDto<List<LoanResDto>>> getAllLoans() {
        List<LoanResDto> loans = loanService.getAllLoans()
                .stream()
                .map(LoanMapper::toResDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(RespondDto.<List<LoanResDto>>builder().httpStatusCode(HttpStatus.OK.value()).message("Loans retrieved").data(loans).build());
    }


    @GetMapping("/loan/current-loan")
    public ResponseEntity<RespondDto<LoanResDto>> getCurrentUserLoan() {
        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<LoanResDto>builder().data(loanService.getCurrentUserLoanLoan()).build());
    }

}
