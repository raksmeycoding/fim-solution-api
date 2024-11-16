package com.fimsolution.group.app.dto.business.f2f.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResDto {

    private String userId;

    private String givenName;

    private String middleName;

    private String familyName;

    private String nickName;

    @Email(message = "A valid email must be provided")
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be blank")
    private String email;

}
