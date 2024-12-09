package com.fimsolution.group.app.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private boolean isAdmin;
    private boolean isRegisterUser;
    private boolean other;
}
