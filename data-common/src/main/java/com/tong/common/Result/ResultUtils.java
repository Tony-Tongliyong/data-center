package com.tong.common.Result;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: ResultUtils
 * @time: 2019/7/13 11:09
 * @desc:
 */
public class ResultUtils {

    public static JSONResult success(Object data){
        JSONResult jsonResult = new JSONResult();
        jsonResult.setData(data);
        jsonResult.setCode("200");
        jsonResult.setMessage("成功");
        return jsonResult;
    }

    public static JSONResult success(){
        JSONResult jsonResult = new JSONResult();
        jsonResult.setCode("200");
        jsonResult.setMessage("成功");
        return jsonResult;
    }

    public static JSONResult failure(){
        JSONResult jsonResult = new JSONResult();
        jsonResult.setCode("500");
        jsonResult.setMessage("失败");
        return jsonResult;
    }
}