package com.fimsolution.group.app.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    @Schema(description = "The email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "The password of the user", example = "P@ssw0rd!")
    private String password;
}
