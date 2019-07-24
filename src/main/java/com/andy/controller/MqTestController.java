package com.andy.controller;

import com.andy.util.mqUtil.MqConsumer;
import com.andy.util.mqUtil.MqSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqTestController {
    @Autowired
    private MqSendUtil mqSendUtil;
    @RequestMapping("test-mq")
    public void sendTest(){
        mqSendUtil.send("测试");
    }

}
