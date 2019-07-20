package com.muc.amqp.service;

import com.muc.amqp.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 使用 @RabbitListener 就需要开启基于注解的RabbitMQ模式
    @RabbitListener(queues = "muc")
    public void receive(User user){
        System.out.println(user);
    }
    
    @RabbitListener(queues = "muc")
    public void receiveHead(Message message){
        System.out.println(message.getBody()); // 内容
        System.out.println(message.getMessageProperties()); // 头信息
    }
}
