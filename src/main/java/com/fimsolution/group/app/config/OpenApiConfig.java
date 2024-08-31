package com.fimsolution.group.app.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Optional;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Raksmey",
                        email = "raksmeykoung@gmail.com"
//                        url = "https://aliboucoding.com/course"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Raksmey",
                version = "1.0",
                license = @License(
                        name = "Licence name"
//                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
//                        url = "https://aliboucoding.com/course"
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

    public static final String X_Request_ID = "X-Request-ID";


//    @Bean
//    public GroupedOpenApi groupedOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("Authentication API")
//                .pathsToMatch("/api/v1/auth/**")
//                .build();
//    }

    @Bean
    public OperationCustomizer customGlobalHeaders() {


        return (Operation operation, HandlerMethod handlerMethod) -> {
            Optional<List<Parameter>> isParameterPresent = Optional.ofNullable(operation.getParameters());
            Boolean isTestHeaderPresent = Boolean.FALSE;
            if (isParameterPresent.isPresent()) {
                isTestHeaderPresent = isParameterPresent.get().stream()
                        .anyMatch(param -> param.getName().equalsIgnoreCase(X_Request_ID));
            }
            if (Boolean.FALSE.equals(isTestHeaderPresent)) {
                Parameter remoteUser = new Parameter().in(ParameterIn.HEADER.toString()).schema(new StringSchema())
                        .name(X_Request_ID).description("X-Request-ID").required(false);
                operation.addParametersItem(remoteUser);
            }
            return operation;
        };
    }


}
