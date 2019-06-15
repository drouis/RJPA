package com.rjpa.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.AMQImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;

public class RabbitChannelListener implements ChannelListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onCreate(Channel channel, boolean b) {
        logger.info("======================onCreate channel: {}, transactional: {}", channel, b);
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {
// 可根据isHardError判断是channel断开还是connection断开
        if (signal.isHardError()) {
            AMQImpl.Connection.Close close = (AMQImpl.Connection.Close) signal.getReason();
            logger.warn("=====================Connection onShutDown replyCode: {}, methodId: {}, classId: {}, replyText: {}",
                    close.getReplyCode(), close.getMethodId(), close.getClassId(), close.getReplyText());
        } else {
            AMQImpl.Channel.Close close = (AMQImpl.Channel.Close) signal.getReason();
            logger.warn("=====================Channel onShutDown replyCode: {}, methodId: {}, classId: {}, replyText: {}",
                    close.getReplyCode(), close.getMethodId(), close.getClassId(), close.getReplyText());
        }
    }
}
