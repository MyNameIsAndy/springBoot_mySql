package com.andy.util.service;


import com.andy.util.BeanFactory;
import com.andy.util.StringUtil;
import org.springframework.core.env.Environment;

/**
 * 获取配置文件内容
 */
public class UtilService {
    public static String getProperties(){
        Environment env =(Environment) BeanFactory.getBean("environment");
        String property = env.getProperty("test.key");
        if(StringUtil.isBlank(property)){
            property = "456";
        }
        return property;
    }

}
