package com.chenxin.playojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author fangchenxin
 * @description
 * @date 2024/6/30 23:12
 * @modify
 */
@Slf4j
public class InitRabbitMq {

    public static final String EXCHANGE_NAME = "question_exchange";

    public static final String QUEUE_NAME = "question_queue";

    public static final String ROUTING_KEY = "question_routingKey";

    public static void doInit() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("admin");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            log.info("消息队列初始化成功");
        } catch (Exception ex) {
            log.error("消息队列初始化失败");
        }
    }
}
