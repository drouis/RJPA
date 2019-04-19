package com.rjpa.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Configuration
@ConfigurationProperties(prefix = "custom")
public class RabbitMQConfiguration {
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

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchangeA() {
        return new DirectExchange(EXCHANGE_SMS);
    }

    @Bean
    public DirectExchange defaultExchangeB() {
        return new DirectExchange(EXCHANGE_EMAIL);
    }

    @Bean
    public DirectExchange defaultExchangeC() {
        return new DirectExchange(EXCHANGE_QUARTZ);
    }

    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }

    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(defaultExchangeA()).with(RabbitMQConfiguration.ROUTINGKEY_SMS);
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(defaultExchangeB()).with(RabbitMQConfiguration.ROUTINGKEY_EMAIL);
    }

    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(defaultExchangeC()).with(RabbitMQConfiguration.ROUTINGKEY_QUARTZ);
    }

    //配置fanout_exchange 绑定到交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConfiguration.FANOUT_EXCHANGE);
    }

    //把所有的队列都绑定到这个交换机上去
    @Bean
    Binding bindingExchangeA(Queue queueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue queueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueC).to(fanoutExchange);
    }
}
