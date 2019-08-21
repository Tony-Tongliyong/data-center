package com.tong.common.Result;

import lombok.Data;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: ResultUtils
 * @time: 2019/7/13 11:09
 * @desc:
 */
@Data
public class ResponseResult<T> {

    private String code;

    private String message;

    T data;

    /**
     * 有返回值
     * @param data
     * @return
     */
    public static ResponseResult success(Object data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(data);
        responseResult.setCode("200");
        responseResult.setMessage("成功");
        return responseResult;
    }

    /**
     * 无返回值
     * @return
     */
    public static ResponseResult success(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode("200");
        responseResult.setMessage("成功");
        return responseResult;
    }

}