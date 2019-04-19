package com.rjpa.rabbitMq;

import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;

public class RabbitConnectionListener implements ConnectionListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onClose(Connection connection) {
        logger.info("================onClose: {}", connection);
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {
        logger.info("================onShutDown: {}", signal);
    }

    @Override
    public void onCreate(Connection connection) {
        logger.info("================onCreate: {}", connection);
    }
}
