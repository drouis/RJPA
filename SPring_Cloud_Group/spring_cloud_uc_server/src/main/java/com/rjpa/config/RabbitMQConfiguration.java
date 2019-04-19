package com.rjpa.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class RabbitMQConfiguration {
    @Value("${custom.spring.rabbitmq.host}")
    private String host;
    @Value("${custom.spring.rabbitmq.port}")
    private int port;
    @Value("${custom.spring.rabbitmq.username}")
    private String username;
    @Value("${custom.spring.rabbitmq.password}")
    private String password;

    public static final String FANOUT_EXCHANGE = "QUEUE_ROAUTER";

    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";


    public static final String QUEUE_A = "QUEUE_SMS";
    public static final String QUEUE_B = "QUEUE_EMAIL";
    public static final String QUEUE_C = "QUEUE_QUARTZ";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
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
        return new DirectExchange(EXCHANGE_A);
    }

    @Bean
    public DirectExchange defaultExchangeB() {
        return new DirectExchange(EXCHANGE_B);
    }

    @Bean
    public DirectExchange defaultExchangeC() {
        return new DirectExchange(EXCHANGE_C);
    }

    //配置fanout_exchange 绑定到交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConfiguration.FANOUT_EXCHANGE);
    }

    /**
     * 获取队列A
     *
     * @return
     */
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }

    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }

    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }


    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchangeA()).with(RabbitMQConfiguration.ROUTINGKEY_A);
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(defaultExchangeB()).with(RabbitMQConfiguration.ROUTINGKEY_B);
    }

    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(defaultExchangeC()).with(RabbitMQConfiguration.ROUTINGKEY_C);
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
