package com.fimsolution.group.app.dto.business.f2f;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDto {

    @JsonIgnore
    String userId;

    String givenName;

    String middleName;

    String familyName;

    String nickName;


    @JsonProperty // Ensure this field is included in the response
    public String getUserId() {
        return userId;
    }


}
