package com.fimsolution.group.app.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


    @Schema(description = "The first name of the user", example = "John")
    @NotBlank(message = "Email is mandatory")
    @NotNull(message = "Email can not be null")
    @NotEmpty(message = "Email can be empty")
    private String firstName;

    @Schema(description = "The last name of the user", example = "Doe")
    @NotBlank(message = "Email is mandatory")
    @NotNull(message = "Email can not be null")
    @NotEmpty(message = "Email can be empty")
    private String lastName;

    @Schema(description = "The email address of the user", example = "john.doe@example.com")
    @NotBlank(message = "Email is mandatory")
    @NotNull(message = "Email can not be null")
    @NotEmpty(message = "Email can be empty")
    @Email(message = "Must be provided a valid email")
    private String email;

    @Schema(description = "The password of the user", example = "P@ssw0rd!")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Schema(description = "The confirmed password of the user", example = "P@ssw0rd!")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String confirmPassword;

    @Schema(description = "The phone number of the user", example = "+1234567890")
    private String phone;



}
