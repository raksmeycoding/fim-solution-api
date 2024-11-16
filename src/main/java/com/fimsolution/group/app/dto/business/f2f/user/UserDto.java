package com.fimsolution.group.app.dto.business.f2f.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AliasFor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    private String givenName;

    private String middleName;

    private String familyName;

    private String nickName;

    private String email;;


}
