package com.tong.datax.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: Writer
 * @time: 2019/7/12 14:17
 * @desc:
 */
@Data
public class Writer {

    /**
     * 任务名称
     */
    private final String name = "mysqlwriter";
    /**
     * 写入类型
     */
    String writeMode = "insert";
    /**
     * 用户名
     */
    String userName;
    /**
     * 密码
     */
    String password;
    /**
     * 字段
     */
    List<String> columns;
    /**
     * session值
     */
    final String session = "set session sql_mode='ANSI'";
    /**
     * 预执行语句
     */
    String preSql;
    /**
     * 表名
     */
    String table;
    /**
     * 连接地址
     */
    String jdbcUrl;

    private String makeJson(){
        StringBuffer sb = new StringBuffer();
        JSONObject writer = new JSONObject();
        writer.put("name",name);

        JSONObject parameter = new JSONObject();
        parameter.put("username",userName);
        parameter.put("password",password);
        JSONArray array = new JSONArray();
        columns.forEach(column->{
            array.add(column);
        });
        parameter.put("column",array);
        parameter.put("writeMode",writeMode);
        parameter.put("preSql",preSql);
        parameter.put("session",session);
        JSONObject connection = new JSONObject();
        JSONArray tableJson = new JSONArray();
        tableJson.add(table);
        connection.put("table",tableJson);
        JSONArray urlJson = new JSONArray();
        urlJson.add(jdbcUrl);
        connection.put("jdbcUrl",urlJson);
        parameter.put("connection",connection);

        writer.put("parameter",parameter);
        sb.append(writer);
        return sb.toString();
    }
}