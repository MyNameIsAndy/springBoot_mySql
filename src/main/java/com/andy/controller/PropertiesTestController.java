package com.andy.controller;

import com.andy.util.service.UtilService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取配置文件内容
 */
@RestController
public class PropertiesTestController {
    @RequestMapping("test-getProperties")
    public String getProperties(){
        String properties = UtilService.getProperties();
        return properties;
    }
}
