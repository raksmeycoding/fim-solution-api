package com.fimsolution.group.app.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ProfileHelper {
    private final Environment environment;

    public String getCurrentProfile() {

        String[] activeProfile = environment.getActiveProfiles();

        if (activeProfile.length > 0) {
            return activeProfile[0];
        }
        return "No active profile found";
    }

    ;
}
