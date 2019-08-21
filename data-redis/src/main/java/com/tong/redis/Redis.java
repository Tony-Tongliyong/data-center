package com.tong.redis;

import com.tong.common.Result.ResponseResult;
import com.tong.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: RedisTest
 * @time: 2019/8/20 17:45
 * @desc:
 */
@RestController
public class Redis {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "getRedis/")
    public ResponseResult get(String key){
        Object result = redisUtils.getObject(key);
        return ResponseResult.success(result);
    }

    @RequestMapping(value = "setRedis/")
    public ResponseResult set(String key,String value){
        redisUtils.set(key,value);
        return ResponseResult.success();
    }
}