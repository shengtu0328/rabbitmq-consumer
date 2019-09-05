package com.xrq.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.xrq.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {


    /**
     * 配置当前的这个消费者监听order-queue这个队列
     * <p>
     * 并且在这里以注解的方式配置之后，15672端口的Rabbitmq management管理平台中就不需要自己手动配置了
     **/
    @RabbitListener(
            bindings = @QueueBinding(

                    //配置队列，durable是否持久化
                    value = @Queue(value = "order-queue", durable = "true"),

                    //配置exchange，durable是否持久化，topic类型
                    exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"),

                    //路由key为 order.*的 都会进入到order-exchange，并且到 order-queue中
                    key = "order.*"
            )
            //
    )


    //此注解代表如果有消息过来，就调用此方法进行消费
    @RabbitHandler
    public void onOrderMessage(
            /*@Payload是springframework.messaging的注解，payload 可以理解为一系列信息中最为关键的信息，算是消息体内容。因为知道传消息体是Order，所以这里可以直接定义成Order类型*/
            @Payload Order order,

            /*手工签收必须依赖于channel*/
            Channel channel,

            /*消息头 properties*/
            @Headers Map<String, Object> headers
    ) throws Exception {
        //消费者操作
        System.err.println("------------收到消息，开始消费------------");
        System.err.println("订单id" + order.getId());

        //手工签收必须要调用channel里的一个方法
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        //手工确认签收 ，消费者消费完消息主动的告诉mq我已经消费完了，false是不支持批量接受
        //一般的工作中也是使用手动的ack，消费完之后要确认
        channel.basicAck(deliverTag, false);
    }

}
