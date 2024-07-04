package com.nadiannis.consume_rest_api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean("restTemplateSmsGateway")
    public RestTemplate restTemplateSmsGateway(RestTemplateBuilder builder) {
        return builder.build();
    }

}
