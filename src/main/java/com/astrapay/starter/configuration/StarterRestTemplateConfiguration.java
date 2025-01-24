package com.astrapay.starter.configuration;

import com.astrapay.starter.service.HttpHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class StarterRestTemplateConfiguration {
    private static final int READ_TIMEOUT_DURATION = 120000;
    private static final int CONNECTION_TIMEOUT_DURATION = 30000;

    @Autowired
    private HttpHeaderService httpHeaderService;

    @Bean
    public RestTemplate starterRestTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMillis(READ_TIMEOUT_DURATION))
                .setConnectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT_DURATION))
                .interceptors(new RestTemplateInterceptorConfiguration(httpHeaderService))
                .build();
    }
}