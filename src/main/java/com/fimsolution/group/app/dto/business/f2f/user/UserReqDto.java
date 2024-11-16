package com.fimsolution.group.app.dto.business.f2f.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReqDto {

    @Schema(description = "Given Name", example = "Liam")
    private String givenName;


    @Schema(description = "Middle Name", example = "Alexander")
    private String middleName;

    @Schema(description = "Family Name", example = "Thompson")
    private String familyName;


    @Schema(description = "Family Name", example = "Ace")
    private String nickName;

    @Email(message = "Email must be a valid email")

    private String email;
}
