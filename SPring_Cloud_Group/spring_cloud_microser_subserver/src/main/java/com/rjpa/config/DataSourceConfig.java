package com.rjpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "driverSchoolDataSourceProperties")
    @Qualifier("driverSchoolDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.driverschool.hikari")
    public DataSourceProperties driverSchoolDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "driverSchoolDataSource")
    @Qualifier("driverSchoolDataSource")
    public DataSource driverSchoolDataSource() {
        DataSourceProperties properties = driverSchoolDatasourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword()).build();
    }

    @Primary
    @Bean(name = "driverSchoolJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("driverSchoolDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "quartzDataSourceProperties")
    @Qualifier("quartzDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.quartz.hikari")
    public DataSourceProperties quartzDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "quartzDataSource")
    @Qualifier("quartzDataSource")
    public DataSource quartzDataSource() {
        DataSourceProperties properties = quartzDatasourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword()).build();
    }

    @Bean(name = "quartzJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("quartzDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
