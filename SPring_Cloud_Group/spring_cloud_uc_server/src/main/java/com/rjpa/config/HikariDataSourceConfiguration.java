package com.rjpa.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@PropertySource("classpath:hikari.properties")
public class HikariDataSourceConfiguration {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    // Hikari 连接池
    //primary将当前数据库连接池作为默认数据库连接池
    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        logger.info("Hikari注入成功！！");
        logger.info("MySql地址：" + url);
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setMinimumIdle(3);
        ds.setMaximumPoolSize(10);
        ds.setIdleTimeout(600000);
        ds.setMaxLifetime(500000);
        return ds;
    }
}
