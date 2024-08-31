package com.fimsolution.group.app.utils;

import java.util.List;
import java.util.function.Function;

public class WhiteListEndpoint {

    // Define an array of whitelisted endpoints as a constant
    public static final String[] WHITE_LIST_ENDPOINTS =
            {
                    "/actuator/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/api/v1/auth/**",
            };





}
