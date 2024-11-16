package com.fimsolution.group.app.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserInfo {
    private String email;
    private String username;
    private List<String> roles;
    private String token;
}
