package com.fimsolution.group.app.mapper.business.f2f;

import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;
import com.fimsolution.group.app.model.business.f2f.LoanUser;

import java.util.List;
import java.util.stream.Collectors;

public class LoanUserMapper {
    // Convert LoanUser to LoanUserDto
    public static LoanUserDto toDto(LoanUser loanUser) {
        if (loanUser == null) {
            return null;
        }

        return LoanUserDto.builder()
                .id(loanUser.getId())
                .loanName(loanUser.getLoanName())
                .role(loanUser.getRole())
                .type(loanUser.getType())
                .email(loanUser.getEmail())
//                .text(loanUser.getText())
//                .phone(loanUser.getPhone())
//                .address(loanUser.getAddress())
                .prioritize(loanUser.getPrioritize())
                .memo(loanUser.getMemo())
//                .user(loanUser.getUser())
//                .events(loanUser.getEvents())
                .build();
    }

    // Convert LoanUserDto to LoanUser
    public static LoanUser toEntity(LoanUserDto dto) {
        if (dto == null) {
            return null;
        }

        LoanUser loanUser = new LoanUser();
        loanUser.setId(dto.getId());
        loanUser.setLoanName(dto.getLoanName());
        loanUser.setRole(dto.getRole());
        loanUser.setType(dto.getType());
        loanUser.setEmail(dto.getEmail());
//        loanUser.setText(dto.getText());
//        loanUser.setPhone(dto.getPhone());
//        loanUser.setAddress(dto.getAddress());
        loanUser.setPrioritize(dto.getPrioritize());
        loanUser.setMemo(dto.getMemo());
//        loanUser.setUser(dto.getUser());
        loanUser.setEvents(dto.getEvents());

        return loanUser;
    }

    // Convert LoanUserReqDto to LoanUser
    public static LoanUser toEntity(LoanUserReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }

        LoanUser loanUser = new LoanUser();
        loanUser.setLoanName(reqDto.getLoanName());
        loanUser.setRole(reqDto.getRole());
        loanUser.setType(reqDto.getType());
        loanUser.setEmail(reqDto.getEmail());
        loanUser.setPrioritize(reqDto.getPrioritize());
        loanUser.setMemo(reqDto.getMemo());

        return loanUser;
    }

    // Convert LoanUser to LoanUserResDto
    public static LoanUserResDto toResDto(LoanUser loanUser) {
        if (loanUser == null) {
            return null;
        }

        return LoanUserResDto.builder()
                .loanId(loanUser.getId())
                .loanName(loanUser.getLoanName())
                .role(loanUser.getRole())
                .type(loanUser.getType())
                .email(loanUser.getEmail())
//                .text(loanUser.getText())
//                .phone(loanUser.getPhone())
//                .address(loanUser.getAddress())
                .prioritize(loanUser.getPrioritize())
                .memo(loanUser.getMemo())
//                .user(loanUser.getUser())
                .build();
    }

    // Convert List<LoanUser> to List<LoanUserResDto>
    public static List<LoanUserResDto> toResDtoList(List<LoanUser> loanUsers) {
        return loanUsers.stream()
                .map(LoanUserMapper::toResDto)
                .collect(Collectors.toList());
    }
}
