package com.rjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryQuartz",
        transactionManagerRef = "transactionManagerQuartz",
        basePackages = {"com.rjpa.mic.repository.quartz.*"}) //设置Repository所在位置
public class QuartzDataSourceConfig {

    @Bean(name = "entityManagerQuartz")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryQuartz(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryQuartz")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryQuartz(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(quartzDataSource)
                .properties(properties)
                .packages("com.rjpa.mic.repository.quartz.Entity.*") //设置实体类所在位置
                .persistenceUnit("quartzPersistenceUnit")
                .build();
    }

    @Bean(name = "transactionManagerQuartz")
    public PlatformTransactionManager transactionManagerQuartz(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryQuartz(builder).getObject());
    }

    @Autowired
    @Qualifier("quartzDataSource")
    private DataSource quartzDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;
}
