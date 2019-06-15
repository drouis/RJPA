package com.rjpa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Primary
@Configuration
@PropertySource("classpath:c3p0.properties")
public class C3P0DataSourceConfiguration {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${c3p0.host}")
    private String host;
    @Value("${c3p0.port}")
    private int port;
    @Value("${c3p0.jdbcUrl}")
    private String jdbcUrl;

    //指定当前对象作为bean
    @Bean(name = "dataSource")
    //指定dataSource来DI
    @Qualifier(value = "dataSource")
    //在application.properties文件中增加前缀c3p0
    @ConfigurationProperties(prefix = "c3p0")
    public DataSource dataSource() {
        logger.info("C3P0注入成功！！");
        logger.info("MySql地址：" + host + ":" + port);
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }
}
