package com.fimsolution.group.app.dto.auth;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiResponse
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RoleResDto {
    private List<String> roles = new ArrayList<>();
}
