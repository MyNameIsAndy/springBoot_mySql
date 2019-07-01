package com.andy.util.redisUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Classname RedisLock
 * @Description redis基础设置
 * @Date 2019-06-28 17:03
 * @Created by Andy
 */
public class RedisLock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisTemplate redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 60 * 1000;

    private volatile boolean locked = false;

    public RedisLock (RedisTemplate redisTemplate,String lockKey){
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey+"_lock";
    }
    public synchronized boolean lock() throws InterruptedException {
        //锁的到期时间，防止无限制锁
        int timeout = expireMsecs;
        while(timeout>=0){
            long expireMsec = System.currentTimeMillis() + expireMsecs + 1;
            String expireMse = String.valueOf(expireMsec);
            if(this.setNx(lockKey,expireMse)){
                locked = true;
                return true;
            }
            //获取缓存时间
             String currentValueStr = this.get(lockKey);
            if(null != currentValueStr && Long.parseLong(currentValueStr)<System.currentTimeMillis()){
                //如果currentValueStr不是空就说明有人抢到了锁，第二个条件就进不去
                //获取上个锁的时间
                String oldValueStr = this.getSet(lockKey, expireMse);
                if(null != oldValueStr && oldValueStr.equals(currentValueStr)){
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;

            /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }
        return false;
    }
    public String getSet(final String key,final String value){
        Object obj = null;
        try{
            obj = redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    byte[] val = redisConnection.getSet(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(value));
                    redisConnection.close();
                    return stringRedisSerializer.deserialize(val);
                }
            });
        }catch (Exception e){
            logger.error("getSet is error Key【{}】",key);
        }
        return obj !=null ? (String)obj : null;
    }
    public String get(final String key){
        Object obj = null;
        try{
            obj = redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    byte[] bytes = redisConnection.get(stringRedisSerializer.serialize(key));
                    redisConnection.close();
                    if(null == bytes){
                        return null;
                    }
                    return stringRedisSerializer.deserialize(bytes);
                }
            });
        }catch (Exception e){
            logger.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }
    public Boolean setNx(final String key,final String value){
        Object obj = null;
        try{
            Object execute = redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    Boolean booleans = redisConnection.setNX(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(value));
                    redisConnection.close();
                    return booleans;
                }
            });
        }catch (Exception e){
            logger.error("setNX redis error, key : {}", key);
        }
        return obj!= null ? (Boolean)obj : false;
    }
    public synchronized void unlock(){
        if(locked){
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }
}
