package com.mferreira.validadorurl.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        bootstrapMode = BootstrapMode.DEFERRED,
        basePackages = "com.mferreira.validadorurl.repository"
)
public class ApplicationConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class.getName());

    @Autowired
    ApplicationProperties applicationProperties;

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Bean
    public DataSource getDataSource()
    {
        LOGGER.info("datasource url: " + datasourceURL);
        return DataSourceBuilder.create()
                .url(datasourceURL).build();
    }
}
