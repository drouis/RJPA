package com.rjpa.mic.rabbitMQ;

import com.rjpa.mic.RabbitConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfiguration.QUEUE_A)
public class CustomEmailRabbitMQReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("处理器one接收处理队列A当中的消息： " + content);
    }
}
