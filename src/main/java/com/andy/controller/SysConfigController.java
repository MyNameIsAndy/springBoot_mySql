package com.andy.controller;

import com.andy.service.SysConfigService;
import com.andy.util.StringUtil;
import com.andy.util.redisUtil.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * @Classname SysConfigController
 * @Description 系统参数
 * @Date 2019-06-28 13:44
 * @Created by Andy
 */
@RestController
@Slf4j
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;
    private int num = 20;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/testLock")
    public String testLock(String test) throws InterruptedException {
//            RedisLock redisLock = new RedisLock(redisTemplate,"redis");
//            boolean lock = redisLock.lock();
//            String name = Thread.currentThread().getName()+"======="+test;
//            if(num >0 && lock){
//                log.info(name + "排号成功，号码是：" + num);
//                num--;
//            }else{
//                log.info(name + "排号失败，号码已经被抢光");
//            }
//            redisLock.unlock();
        Jedis jedis = new Jedis();
        String s = UUID.randomUUID().toString();
        String result = jedis.set("redis",s , "NX", "PX", 10000);
        String name = Thread.currentThread().getName()+"======="+test;
        if(num >0 && "OK".equals(result)){
            log.info(name + "排号成功，号码是：" + num);
            num--;
        }else{
            log.info(name + "排号失败，号码已经被抢光");
        }
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        jedis.eval(script, Collections.singletonList("redis"), Collections.singletonList(s));
        return "";
    }
    @RequestMapping("/test")
    public String test(String test) throws InterruptedException {
        Jedis jedis = new Jedis("",6379);
        String name = Thread.currentThread().getName()+"======="+test;
        if(num>0){
            log.info(name + "排号成功，号码是：" + num);
            num--;
        }else{
            log.info(name + "排号失败，号码已经被抢光");
        }
        return name;
    }
//    public boolean lock(String code){
//        RedisLock redisLock = new RedisLock(redisTemplate,code);
//        boolean flag = false;
//        try{
//            flag = redisLock.lock();
//            if(flag){
//                log.info("------------------------------抢到锁了--------------------------------"+uuid);
//
//
//            }
//        }catch (Exception e){
//            log.debug("------------------------------未获取到锁--------------------------------"+uuid);
//        }finally {
//            //解锁
//            if(flag){
//                log.debug("------------------------------解锁成功--------------------------------"+uuid);
//                redisLock.unlock();
//            }
//        }
//        return false;
//    }
}

