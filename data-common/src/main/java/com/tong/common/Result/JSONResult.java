package com.tong.common.Result;

import lombok.Data;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: JSONResult
 * @time: 2019/7/13 10:57
 * @desc:
 */
@Data
public class JSONResult<T> {

    private String code;

    private String message;

    T data;


}