package com.pdv.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final String PYTHON_URL;

    public WebClientConfig(@Value("${python.url}") String python_api) {
        this.PYTHON_URL = python_api;
    }

    @Bean
    public WebClient pythonWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(PYTHON_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}