package com.chenxin.playojbackendjudgeservice.rabbitmq;

import com.chenxin.playojbackendjudgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fangchenxin
 * @description 消息消费
 * @date 2024/6/30 23:42
 * @modify
 */
@Slf4j
@Component
public class QuestionConsumer {

    public static final String QUEUE_NAME = "question_queue";

    @Resource
    private JudgeService judgeService;

    @SneakyThrows
    @RabbitListener(queues = QUEUE_NAME, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage={}", message);
        try {
            long userQuestionId = Long.parseLong(message);
            judgeService.doJudge(userQuestionId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception ex) {
            channel.basicNack(deliveryTag, false, false);
        }
    }


}
