package com.fimsolution.group.app.dto.business;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfile {
    private String id;
    private String username;
}
