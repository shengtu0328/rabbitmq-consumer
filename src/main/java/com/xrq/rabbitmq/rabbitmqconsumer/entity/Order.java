package com.xrq.rabbitmq.rabbitmqconsumer.entity;

import java.io.Serializable;

/**
 * @program: rabbitmq-consumer
 * @description:
 * @author: rqxiao
 * @create: 2018-08-31 16:44
 *
 *  在mq中发送的信息需要序列化，所以要实现Serializable接口
 **/
public class Order implements Serializable {

    private String id;
    private String name;
    private String messageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
