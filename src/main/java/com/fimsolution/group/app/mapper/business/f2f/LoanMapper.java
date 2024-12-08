package com.fimsolution.group.app.mapper.business.f2f;

import com.fimsolution.group.app.dto.business.f2f.LoanDto;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanReqDto;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanResDto;
import com.fimsolution.group.app.model.business.f2f.Loan;

public class LoanMapper {

    public static Loan fromLoanDtoToEntity(LoanDto loanDto) {
        return Loan.builder()
                .fee(loanDto.getFee())
                .isLinkToLoanUser(false)
                .amount(loanDto.getAmount())
                .note(loanDto.getNote())
                .interest(loanDto.getInterest())
                .createdDate(loanDto.getCreatedDate())
                .endDate(loanDto.getEndDate())
                .nickName(loanDto.getNickName())
                .loanType(loanDto.getLoanType())
                .build();
    }


    public static LoanDto fromLoanEntityToLoanDto(Loan loan) {
        return LoanDto.builder()
                .id(loan.getId())
                .fee(loan.getFee())
                .isLinkToLoanUser(loan.isLinkToLoanUser())
                .amount(loan.getAmount())
                .note(loan.getNote())
                .createdDate(loan.getCreatedDate())
                .endDate(loan.getEndDate())
                .interest(loan.getInterest())
                .loanType(loan.getLoanType())
                .build();
    }

    // Convert Loan to LoanDto
    public static LoanDto toDto(Loan loan) {
        if (loan == null) {
            return null;
        }

        return LoanDto.builder()
                .id(loan.getId())
//                .loanId(loan.getLoanId())
                .nickName(loan.getNickName())
                .interest(loan.getInterest())
                .loanType(loan.getLoanType())
                .amount(loan.getAmount())
                .fee(loan.getFee())
                .note(loan.getNote())
                .createdDate(loan.getCreatedDate())
                .endDate(loan.getEndDate())
                .isLinkToLoanUser(loan.isLinkToLoanUser())
                .events(loan.getEvents())
                .build();
    }

    // Convert LoanDto to Loan
    public static Loan toEntity(LoanDto dto) {
        if (dto == null) {
            return null;
        }

        Loan loan = new Loan();
        loan.setId(dto.getId());
//        loan.setLoanId(dto.getLoanId());
        loan.setNickName(dto.getNickName());
        loan.setInterest(dto.getInterest());
        loan.setLoanType(dto.getLoanType());
        loan.setAmount(dto.getAmount());
        loan.setFee(dto.getFee());
        loan.setNote(dto.getNote());
        loan.setCreatedDate(dto.getCreatedDate());
        loan.setEndDate(dto.getEndDate());
        loan.setLinkToLoanUser(dto.isLinkToLoanUser());
        loan.setEvents(dto.getEvents());

        return loan;
    }

    // Convert LoanRequestDto to Loan
    public static Loan toEntity(LoanReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }

        Loan loan = new Loan();
        loan.setNickName(reqDto.getNickName());
        loan.setInterest(reqDto.getInterest());
        loan.setLoanType(reqDto.getLoanType());
        loan.setAmount(reqDto.getAmount());
        loan.setFee(reqDto.getFee());
        loan.setNote(reqDto.getNote());
        loan.setCreatedDate(reqDto.getCreatedDate());
        loan.setEndDate(reqDto.getEndDate());
        loan.setLinkToLoanUser(reqDto.isLinkToLoanUser());

        return loan;
    }

    // Convert Loan to LoanResDto
    public static LoanResDto toResDto(Loan loan) {
        if (loan == null) {
            return null;
        }

        return LoanResDto.builder()
                .id(loan.getId())
                .nickName(loan.getNickName())
                .interest(loan.getInterest())
                .loanType(loan.getLoanType())
                .amount(loan.getAmount())
                .fee(loan.getFee())
                .note(loan.getNote())
                .createdDate(loan.getCreatedDate())
                .endDate(loan.getEndDate())
                .isLinkToLoanUser(loan.isLinkToLoanUser())
                .build();
    }

}
