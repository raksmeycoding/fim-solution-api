package com.fimsolution.group.app.dto.auth;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseUserInfo {
    private String email;
    private String username;
    private List<String> roles;
}
