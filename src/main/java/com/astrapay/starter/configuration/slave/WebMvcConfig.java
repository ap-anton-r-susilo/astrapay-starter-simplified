package com.astrapay.starter.configuration.slave;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(value = "master-slave-configuration.enabled", havingValue = "true")
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public OpenEntityManagerInViewFilter securityOpenEntityManagerInViewFilter()
    {
        OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
        osivFilter.setEntityManagerFactoryBeanName("masterEntityManagerFactory");
        return osivFilter;
    }

    @Bean
    public OpenEntityManagerInViewFilter ordersOpenEntityManagerInViewFilter()
    {
        OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
        osivFilter.setEntityManagerFactoryBeanName("readOnlyEntityManagerFactory");
        return osivFilter;
    }
}
