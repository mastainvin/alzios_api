package com.alzios.api.configs;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("Alzios API V1")
                .pathsToMatch("/v1/**")
                .build();
    }

}
