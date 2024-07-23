package com.fc.financecontrolapi.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public String tokenSecret(){
        return dotenv.get("TOKEN_SECRET");
    }
}
