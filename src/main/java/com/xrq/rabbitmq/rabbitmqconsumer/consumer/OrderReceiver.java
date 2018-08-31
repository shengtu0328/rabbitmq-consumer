package com.xrq.rabbitmq.rabbitmqconsumer.consumer;

import com.rabbitmq.client.Channel;
import com.xrq.rabbitmq.rabbitmqconsumer.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {

    @RabbitHandler
    public void onOrderMessage(@Payload Order order, /*因为知道传消息体是order，所以这里可以直接定义成Order类型*/
                               @Headers Map<String,Object> headers, /*消息头*/
                               Channel channel)throws Exception{/*手工签收必须依赖于channel*/
        //消费者操作：
        System.err.println("------------收到消息，开始消费------------");
        System.err.println("订单id"+order.getId());


        Long deliverTag=(Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliverTag,false);
    }

}
