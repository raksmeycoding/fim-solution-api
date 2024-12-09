package com.fimsolution.group.app.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
@RequiredArgsConstructor
public class ProductionRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
