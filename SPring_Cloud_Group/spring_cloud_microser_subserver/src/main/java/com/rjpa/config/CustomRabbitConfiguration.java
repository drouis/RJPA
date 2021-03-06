package com.rjpa.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRabbitConfiguration {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String FANOUT_EXCHANGE = "QUEUE_ROAUTER";

    public static final String EXCHANGE_SMS = "CUSTOM-SMS-MQ-EXCHANGE";
    public static final String EXCHANGE_EMAIL = "CUSTOM-EMAIL-MQ-EXCHANGE";
    public static final String EXCHANGE_QUARTZ = "CUSTOM-QUARTZ-MQ-EXCHANGE";


    public static final String QUEUE_A = "QUEUE_SMS";
    public static final String QUEUE_B = "QUEUE_EMAIL";
    public static final String QUEUE_C = "QUEUE_QUARTZ";

    public static final String ROUTINGKEY_SMS = "spring-boot-routingKey_SMS";
    public static final String ROUTINGKEY_EMAIL = "spring-boot-routingKey_EMAIL";
    public static final String ROUTINGKEY_QUARTZ = "spring-boot-routingKey_QUARTZ";


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setUri("amqp://" + username + ":" + password + "@" + host + ":" + port);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //设置忽略声明异常
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }
}
