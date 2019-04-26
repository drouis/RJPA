package com.rjpa.rabbitMQ;

import com.rjpa.config.CustomRabbitConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = CustomRabbitConfiguration.QUEUE_B)
public class CustomSMSRabbitMQReceiverBack {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("处理器two接收处理队列B当中的消息： " + content);
    }
}
