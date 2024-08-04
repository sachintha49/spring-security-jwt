package com.spring_security_jwt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    //api me tikata access dunna. neththam security visin meka block karala thiyenne
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    //meken apita limit karaganna puluwan mewata mewata access denna kiyala. meke ape okkoma tikata acccess deela thiyenne
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //meketh methods, ehaders, patterns walatath access dunna 
            registry.addMapping("/**")
                    .allowedMethods(GET,POST,PUT,DELETE)
                    .allowedHeaders("*")
                    .allowedOriginPatterns("*")
                    .allowCredentials(true);
            }
        };
    }
}
