package com.andy.util.mqUtil;

import com.alibaba.fastjson.JSONObject;
import com.andy.util.JsonUtil;
import com.andy.vo.QueueModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmqpProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private JsonUtil jsonUtil;

    /**
     * 发送消息，queueName是发送到的队列的名称，message是待发送的消息
     *
     * @param queueName
     * @param message
     * @param remark
     * @author apple
     * @date 2018-01-07
     */
    public void sendMessage(String queueName, final String message, final String remark) {
        QueueModel queueModel = new QueueModel();
        queueModel.setQueueName(queueName);
        queueModel.setDataId(message);
        queueModel.setRemark(remark);
        rabbitTemplate.convertAndSend(queueName, jsonUtil.object2Json(queueModel));
    }

    /**
     * 发送消息，queueName是发送到的队列的名称，message是待发送的消息
     *
     * @param queueName
     * @param message
     * @author wcf
     * @date 2018-05-23
     */
    public void sendMessage(String queueName, final String message) {
        QueueModel queueModel = new QueueModel();
        queueModel.setQueueName(queueName);
        queueModel.setDataId(message);
        rabbitTemplate.convertAndSend(queueName, jsonUtil.object2Json(queueModel));
    }

    /**
     * 发送消息，queueName是发送到的队列的名称，message是待发送的消息
     *
     * @param queueName QNAME
     * @param message   JsonData
     * @author apple
     * @date 2018-01-07
     */
    public void sendMessage(String queueName, final JSONObject message) {
        rabbitTemplate.convertAndSend(queueName, jsonUtil.object2Json(message));
    }
}
