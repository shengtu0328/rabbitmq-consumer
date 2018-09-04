package com.xrq.rabbitmq.entity;

import java.io.Serializable;

/**
 * @program: rabbitmq-consumer
 * @description:
 * @author: rqxiao
 * @create: 2018-08-31 16:44
 *
 *  Order通过网络中发送到mq，所以一定要实现Serializable接口，producer项目和consumer项目中必须保持一致，包括所在的包，不然消费者会报错，无法反序列化找不到类。Could not deserialize object, ClassNotFoundException
 **/
public class Order implements Serializable {

    private static final long serialVersionUID = 3784667646141688033L;
    private String id;  //order实体普通字段
    private String name;//order实体普通字段
    private String messageId; //**存储消息发送的唯一标识

    public Order(){

    }

    public Order(String id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

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
