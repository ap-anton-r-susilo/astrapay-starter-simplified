package com.astrapay.starter.configuration.slave;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "master-slave-configuration.enabled", havingValue = "true")
@EnableJpaRepositories(
        basePackages = "com.astrapay",
        includeFilters = @ComponentScan.Filter(ReadOnlyRepository.class),
        entityManagerFactoryRef = "readOnlyEntityManagerFactory",
        transactionManagerRef = "slaveTransactionManager"
)
public class SlaveDataSourceConfiguration {

    @Bean("slaveDataSourceProperties")
    @ConfigurationProperties(prefix = "slave.spring.datasource")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "slave.spring.datasource.hikari")
    public HikariDataSource slaveDataSource(
            @Qualifier("slaveDataSourceProperties") DataSourceProperties slaveDataSourceProperties
    ) {
        HikariDataSource dataSource = slaveDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(slaveDataSourceProperties.getName())) {
            dataSource.setPoolName(slaveDataSourceProperties.getName());
        }
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory(
            @Qualifier("slaveDataSource") DataSource slaveDataSource,
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter,
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties
    ) {
        Map<String, String> properties = jpaProperties.getProperties();
        hibernateProperties.determineHibernateProperties(properties,
                        new HibernateSettings().hibernatePropertiesCustomizers(new ArrayList<>()))
                .forEach((key, value) -> properties.put(key, (String) value));

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(slaveDataSource);
        factoryBean.setPackagesToScan(MasterDataSourceConfiguration.BASE_PACKAGES);
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factoryBean.getJpaPropertyMap().putAll(properties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager slaveTransactionManager(
            LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(readOnlyEntityManagerFactory.getObject());
        return transactionManager;
    }
}
