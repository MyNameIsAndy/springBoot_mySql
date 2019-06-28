package com.andy.util.redisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2017/7/25.
 */
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    // private  final ObjectMapper Mapper = new ObjectMapper();

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     *
     * @param pattern
     */
    public void batchDel(String... pattern) {
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 取得缓存（int型）
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!StringUtils.isEmpty(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public String getStr(String key, boolean retain) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return value;
    }

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @return
     */
    public Object getObj(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @param retain 是否保留
     * @return
     */
    public Object getObj(String key, boolean retain) {
        Object obj = redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }


    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     * @param time     失效时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, Long time, TimeUnit timeUnit) {
        if (value.getClass().equals(String.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Integer.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Double.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Float.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Short.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Long.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Boolean.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        if (time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
    }

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void set(String key, Object value, Long time) {
        this.set(key, value, time, TimeUnit.SECONDS);
    }


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    public double decr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     *
     * @param key
     * @param by
     * @return
     */
    public double incr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 递减操作
     *
     * @param key
     * @param field
     * @param by
     * @return
     */
    public long mapDecr(String key, String field, long by) {
        return redisTemplate.opsForHash().increment(key, field, -by);
    }

    /**
     * 递增操作
     *
     * @param key
     * @param field
     * @param by
     * @return
     * @auth
     */
    public long mapIncr(String key, String field, long by) {
        return redisTemplate.opsForHash().increment(key, field, by);
    }

    /**
     * 获取double类型值
     *
     * @param key
     * @return
     */
    public double getDouble(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!StringUtils.isEmpty(value)) {
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setDouble(String key, double value, Long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置Int类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setInt(String key, int value, Long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean existKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     * @param time 失效时间(秒)
     */
    public <T> void setMap(String key, Map<String, T> map, Long time) {
        setMap(key, map, time, TimeUnit.SECONDS);
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     * @param time     失效时间
     * @param timeUnit 失效时间单位
     */
    public <T> void setMap(String key, Map<String, T> map, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     */
    public <T> void setMap(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }


    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key
     * @param map
     */
    public <T> void addMap(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public <T> void addMap(String key, String field, T obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 获取map缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> Map<String, T> mget(String key, Class<T> clazz) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 判断field是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public boolean existMapField(String key, String field) {
        if (key != null && key.trim().length() != 0 && field != null && field.trim().length() != 0) {
            return redisTemplate.opsForHash().hasKey(key, field);
        }
        return false;
    }


    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     */
    public void delMapField(String key, String field) {
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key  缓存KEY
     * @param time 失效时间(秒)
     * @author FangJun
     * @date 2016年8月14日
     */
    public void expire(String key, Long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public void addSet(String key, String value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public void removeSet(String key, String value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     *
     * @param oldkey
     * @param newkey
     */
    public void reNameSet(String oldkey, String newkey) {
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }


    /**
     * 模糊查询keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 添加List(左侧)
     *
     * @param key
     * @param list
     * @param time     生存时间
     * @param timeUnit 时间单位
     */
    public <T> void addList(String key, List<T> list, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForList().leftPushAll(key, list);
        if (time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
    }

    /**
     * 添加List(左侧)
     *
     * @param key
     * @param list
     * @param time 生存时间(秒)
     */
    public <T> void addList(String key, List<T> list, Long time) {
        addList(key, list, time, TimeUnit.SECONDS);
    }

    /**
     * 添加List(左侧)
     *
     * @param key
     * @param list
     */
    public <T> void addList(String key, List<T> list) {
        redisTemplate.opsForList().leftPushAll(key, list);
    }

    /**
     * 设置生存时间
     *
     * @param key
     * @param time     生存时间
     * @param timeUnit 时间单位
     */
    public void expire(String key, Long time, TimeUnit timeUnit) {
        if (time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
    }

    /**
     * 通过key设置list,如该key已经存在,将其返回并删除,然后将新的list设置进去
     *
     * @param key
     * @param list
     * @param time     生存时间
     * @param timeUnit 时间单位
     * @return 如果key存在返回key的值, 如果不存在返回null
     */
    public <T> List<T> setList(String key, List<T> list, Long time, TimeUnit timeUnit) {
        List<T> range = redisTemplate.opsForList().range(key, 0, -1);
        if (range.isEmpty()) {
            return null;
        }
        redisTemplate.delete(key);
        if (time > 0) {
            addList(key, list, time, timeUnit);
        } else {
            addList(key, list);
        }
        return range;
    }

    /**
     * 通过key设置list,如该key已经存在,将其返回并删除,然后将新的list设置进去
     *
     * @param key
     * @param list
     * @param seconds 生存时间(秒)
     * @return 如果key存在返回key的值, 如果不存在返回null
     */
    public <T> List<T> setList(String key, List<T> list, Long seconds) {
        return this.setList(key, list, seconds, TimeUnit.SECONDS);
    }

    /**
     * 通过key设置list,如该key已经存在,将其返回并删除,然后将新的list设置进去
     *
     * @param key
     * @param list
     * @return 如果key存在返回key的值, 如果不存在返回null
     */
    public <T> List<T> setList(String key, List<T> list) {
        return this.setList(key, list, 0L, TimeUnit.SECONDS);
    }

    /**
     * 在左边添加
     *
     * @param key
     * @param obj
     */
    public void leftPush(String key, Object obj) {
        redisTemplate.opsForList().leftPush(key, obj);
    }

    /**
     * 在右边添加
     *
     * @param key
     * @param obj
     */
    public void rightPush(String key, Object obj) {
        redisTemplate.opsForList().rightPush(key, obj);
    }

    /**
     * 从左边删除并返回该值
     *
     * @param key
     * @param <T>
     * @return 删除的值
     */
    public <T> T leftPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从右边删除,并返回值
     *
     * @param key
     * @param <T>
     * @return 返回删除的值
     */
    public <T> T rightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取list的长度
     *
     * @param key
     * @return
     * @author apple
     * @date 2018-01-07
     */
    public int getListSize(String key) {
        List range = redisTemplate.opsForList().range(key, 0, -1);
        if (range.isEmpty()) {
            return 0;
        } else {
            return range.size();
        }
    }

    /**
     * 获取list中指定开始位置和结束位置的数据集合
     *
     * @param key
     * @param startIndex
     * @param endIndex
     * @param <T>
     * @return
     */
    public <T> List<T> getRangeList(String key, int startIndex, int endIndex) {
        List<T> range = redisTemplate.opsForList().range(key, startIndex, endIndex);
        if (range.isEmpty()) {
            return null;
        } else {
            return range;
        }
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    public boolean flushDB() {
        return (boolean) redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return true;
        });
    }

    /**
     * 删除list指定元素
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long remove(String key, int count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
}

