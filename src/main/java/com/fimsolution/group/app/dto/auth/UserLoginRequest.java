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
    @Schema(description = "The email address of the user", example = "admin@example.com")
    private String email;

    @Schema(description = "The password of the user", example = "2H5pLJdKIzGdrt.&afYy")
    private String password;
}
