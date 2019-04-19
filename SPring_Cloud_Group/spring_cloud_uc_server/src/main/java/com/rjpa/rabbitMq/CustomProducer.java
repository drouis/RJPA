package com.rjpa.rabbitMq;
import com.rjpa.config.RabbitMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomProducer implements RabbitTemplate.ConfirmCallback {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    public CustomProducer(RabbitTemplate rabbitTemplate) {
        //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setUsePublisherConnection(true);
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendSMSMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_A, RabbitMQConfiguration.ROUTINGKEY_A, content, correlationId);
    }

    public void sendEMAILMsg(String content){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_B, RabbitMQConfiguration.ROUTINGKEY_B, content, correlationId);
    }

    public void sendQUARTZMsg(String content){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_C, RabbitMQConfiguration.ROUTINGKEY_C, content, correlationId);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info(" 回调id:" + correlationData);
        if (b) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + s);
        }
    }

    public void sendAll(String content) {
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.FANOUT_EXCHANGE,"", content);
    }
}
