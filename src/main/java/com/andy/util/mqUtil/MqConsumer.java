package com.andy.util.mqUtil;

import com.andy.util.JsonUtil;
import com.andy.vo.QueueModel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqConsumer {
    @Autowired
    private JsonUtil jsonUtil;

    @RabbitHandler
    @RabbitListener(queues = "test", containerFactory = "rabbitContainerFactory")
    public void singleHandelQueueListener(String message){
        QueueModel queueModel = jsonUtil.json2Object(message, QueueModel.class);
        System.out.println(queueModel.getQueueName());
        System.out.println(queueModel.getDataId());
        System.out.println(queueModel.getRemark());
    }
}
