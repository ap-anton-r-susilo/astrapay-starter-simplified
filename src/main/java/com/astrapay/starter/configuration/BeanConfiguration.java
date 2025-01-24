package com.astrapay.starter.configuration;

import com.astrapay.starter.service.string.SignatureGeneratorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public SignatureGeneratorService signatureGeneratorService(){
        return new SignatureGeneratorService();
    }

}
