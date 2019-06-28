package com.andy.controller;

import com.andy.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SysConfigController
 * @Description 系统参数
 * @Date 2019-06-28 13:44
 * @Created by Andy
 */
@RestController
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/sysConfigInit")
    public String SysConfigTest(){
        String cmd_retry_time = sysConfigService.getSysConfig("cmd_retry_time");
        return cmd_retry_time;
    }
}

