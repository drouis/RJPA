package com.rjpa.rabbitMQ;

import com.google.gson.Gson;
import com.rjpa.config.CustomRabbitConfiguration;
import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;
import com.rjpa.mic.service.IAmpqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = CustomRabbitConfiguration.QUEUE_B)
public class CustomSMSRabbitMQReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();

    @RabbitHandler
    public void process(String content) {
        logger.info("处理器Master接收处理队列" + CustomRabbitConfiguration.QUEUE_A + "当中的消息： " + content);
        AmpqMessageEntity message = gson.fromJson(content, AmpqMessageEntity.class);
        ampqService.updateMailAmpqMessage(message);
    }

    @Autowired
    IAmpqService ampqService;
}
