package com.tong.connection;

import com.tong.connection.mysql.MysqlConnection;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: Connection
 * @time: 2019/7/23 14:27
 * @desc:
 */
public class Connection<T> {

    T connection;

    public Boolean connectStatus(){
        if(connection instanceof MysqlConnection){
            return ((MysqlConnection) connection).getConnection() != null;
        }
        return false;
    }

}