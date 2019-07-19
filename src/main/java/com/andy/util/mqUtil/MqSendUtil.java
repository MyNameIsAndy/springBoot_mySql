package com.andy.util.mqUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSendUtil {
    @Autowired
    private AmqpProducer amqpProducer;

    /**
     * 向指定key发送数据
     * @param name
     * @param value
     */
    public void send(final String name, final String value){
        amqpProducer.sendMessage(name,value);
    }

    /**
     *
     * @param value
     */
    public void send(final String value){
        amqpProducer.sendMessage("test",value);
    }
}
