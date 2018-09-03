package com.xrq.rabbitmq.rabbitmqconsumer.consumer;

import com.rabbitmq.client.Channel;
import com.xrq.rabbitmq.rabbitmqconsumer.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange=@Exchange(name="order-exchange",durable = "true",type="topic"),
            key="order.*"
            )//监听了order-queue这个队列
    )
    @RabbitHandler//以注解的方式来监听
    public void onOrderMessage(@Payload Order order, /*@Payload是springframework.messaging的注解，payload 可以理解为一系列信息中最为关键的信息，算是消息体内容。因为知道传消息体是Order，所以这里可以直接定义成Order类型*/
                               @Headers Map<String, Object> headers, /*消息头 properties*/
                               Channel channel) throws Exception {/*手工签收必须依赖于channel*/
        //消费者操作：
        System.err.println("------------收到消息，开始消费------------");
        System.err.println("订单id" + order.getId());

        //手工签收必须要调用channel里的一个方法
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliverTag, false);//手工确认签收 ，消费者消费完消息主动的告诉mq我已经消费完了，false是不支持批量接受
    }

}
