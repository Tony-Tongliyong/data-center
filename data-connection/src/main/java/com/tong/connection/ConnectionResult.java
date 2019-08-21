package com.tong.connection;

import lombok.Data;

import java.sql.Connection;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: ConnectionResult
 * @time: 2019/8/21 15:46
 * @desc:
 */
@Data
public class ConnectionResult {
    /**
     * 连接信息
     */
    Connection connection;
    /**
     * 连接(0:失败，1:成功)
     */
    int success;
    /**
     * 连接日志
     */
    String message;
}