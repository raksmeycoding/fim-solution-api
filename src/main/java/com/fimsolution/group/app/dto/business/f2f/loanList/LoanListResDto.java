package com.fimsolution.group.app.dto.business.f2f.loanList;


import com.fimsolution.group.app.dto.business.f2f.loan.LoanResDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanListResDto {
    List<LoanResDto> loanResDtoList;
}
