package com.muc.amqp;

import com.muc.amqp.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02AmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 使用AmqpAdmin 创建交换器
     */
    @Test
    public void createExchange(){
        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.exchange"));
    }

    /**
     * 创建队列
     */
    @Test
    public void createQueue(){
        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue",true));
    }

    /**
     * 创建绑定规则
     */
    @Test
    public void createBinding(){
        // declareBinding(目的地,目的地类型,交换器,路由键,参数);
        amqpAdmin.declareBinding(new Binding("amqpAdmin.queue", Binding.DestinationType.QUEUE,
                "amqpAdmin.exchange","hahaha",null));
    }


    // ---------------------------
    /**
     * 单播（点对点）
     */
    @Test
    public void contextLoads() {
        // Message需要自己构建一个,定义消息具体内容和消息头(一般不使用这个)
//        rabbitTemplate.send(exchange, routingKey, message);

        // Object默认当做消息体,只需要传入要发送的消息,自动序列化发送给rabbimq
        Map<String,Object> map = new HashMap<>();
        map.put("msg","firstMassage");
        map.put("data", Arrays.asList("helloWorld",123,true));
        map.put("newData", Arrays.asList("helloWorld",456,false));

//        rabbitTemplate.convertAndSend("exchange.direct", "huiya.news",map);
        rabbitTemplate.convertAndSend("exchange.direct", "huiya.news",new User("李四", "lisi"));
    }

    /**
     * 接受消息 接受消息后消息队列中的消息就会清除
     */
    @Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("huiya.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /**
     * 广播(多对多)
     */
    @Test
    public void fanoutSendMsg(){
        rabbitTemplate.convertAndSend("exchange.fanout", "muc", new User("张三", "zhangsan"));
    }

}
