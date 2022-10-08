package com.alzios.api.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("x")
                .exposedHeaders("x")
                /*.allowedHeaders("location", "x-requested-with", "content-type", "accept", "origin", "authorization", "x-csrf-token", "x-auth-token")
                .exposedHeaders("Authorization", "Location")*/
                .allowCredentials(true);

        //registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowCredentials(true).allowedOriginPatterns("*").exposedHeaders("location");
    }

}