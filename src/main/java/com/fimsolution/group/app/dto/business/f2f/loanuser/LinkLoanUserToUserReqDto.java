package com.fimsolution.group.app.dto.business.f2f.loanuser;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkLoanUserToUserReqDto {
    private String userId;
    private String loanUserId;
}
