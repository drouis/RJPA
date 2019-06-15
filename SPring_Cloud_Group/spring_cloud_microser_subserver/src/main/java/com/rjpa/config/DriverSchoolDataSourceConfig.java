package com.rjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = {"com.rjpa.mic.repository.driverschool.*"},//设置Repository所在位置
        entityManagerFactoryRef = "entityManagerFactoryDriverSchool",
        transactionManagerRef = "transactionManagerDriverSchool")
public class DriverSchoolDataSourceConfig {

    @Primary
    @Bean(name = "entityManagerDriverSchool")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryDriverSchool(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryDriverSchool")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDriverSchool(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(driverSchoolDataSource)
                .properties(properties)
                .packages("com.rjpa.mic.repository.driverschool.Entity.*") //设置实体类所在位置
                .persistenceUnit("driverSchoolPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerDriverSchool")
    public PlatformTransactionManager transactionManagerDriverSchool(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryDriverSchool(builder).getObject());
    }

    @Autowired
    @Qualifier("driverSchoolDataSource")
    private DataSource driverSchoolDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;
}
