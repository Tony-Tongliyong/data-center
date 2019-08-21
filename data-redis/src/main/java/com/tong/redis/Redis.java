package com.tong.redis;

import com.tong.common.Result.JSONResult;
import com.tong.common.Result.ResultUtils;
import com.tong.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: RedisTest
 * @time: 2019/8/20 17:45
 * @desc:
 */
@RestController
public class Redis {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "getRedis/")
    public JSONResult get(String key){
        Object result = redisUtils.getObject(key);
        return ResultUtils.success(result);
    }

    @RequestMapping(value = "setRedis/")
    public JSONResult set(String key,String value){
        redisUtils.set(key,value);
        return ResultUtils.success();
    }
}