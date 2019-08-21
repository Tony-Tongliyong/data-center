package com.tong.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: RedisUtils
 * @time: 2019/1/14 9:49
 * @desc:
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存数据
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 保存数据，并设置过期时间（单位是秒）
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(String key, Object value, long expireTime){
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在于redis中
     * @param key
     * @return
     */
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    public String get(String key){
        return (String)redisTemplate.opsForValue().get(key);
    }

    public Object getObject(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 获取key对应的对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz){
        String s = get(key);
        if(StringUtils.isNotEmpty(s)){
            return JSON.toJavaObject(JSON.parseObject(s), clazz);
        }
        return null;
    }

    /**
     * 删除key对应的值
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     * 添加hash类型的值
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, String field, String value){
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 添加hash类型的值
     * @param key
     * @param keyValues map对象
     */
    public void hSet(String key, Map keyValues){
        redisTemplate.opsForHash().putAll(key, keyValues);
    }

    /**
     * 添加hash类型的值，同时设置过期时间（单位：秒）
     * @param key
     * @param field
     * @param value
     * @param seconds
     */
    public void hSet(String key, String field, String value, long seconds){
        this.hSet(key, field, value);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取hash的字段值
     * @param key
     * @param field
     * @return
     */
    public String hGet(String key, String field){
        String value = (String)redisTemplate.opsForHash().get(key, field);
        return value;
    }

    /**
     * 批量获取hash的字段值
     * @param key
     * @param fields
     * @return
     */
    public List<String> hGetAll(String key, List<String> fields){
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 删除hash属性值
     * @param key
     * @param field
     */
    public void hDelete(String key, String field){
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 获取有效期时间，单位秒，比如1，则表示还剩1秒过期
     * @param key
     * @return
     */
    public Long getExpireTime(String key) {
        return redisTemplate.getExpire(key);
    }
}
