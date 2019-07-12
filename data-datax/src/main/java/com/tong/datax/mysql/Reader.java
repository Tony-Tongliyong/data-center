package com.tong.datax.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: Reader
 * @time: 2019/7/12 14:17
 * @desc:
 */
@Data
public class Reader {

    /**
     * 任务名称
     */
    private final String name = "mysqlreader";
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
     * 区分键
     */
    String splitPk;
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
        //reader模块
        JSONObject reader = new JSONObject();
        reader.put("name",name);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("username",userName);
        parameter.put("password",password);
        JSONArray array = new JSONArray();
        columns.forEach(column->{
            array.add(column);
        });
        parameter.put("column",array);
        parameter.put("splitPk",splitPk);
        JSONObject connection = new JSONObject();
        JSONArray tableJson = new JSONArray();
        tableJson.add(table);
        connection.put("table",tableJson);
        JSONArray urlJson = new JSONArray();
        urlJson.add(jdbcUrl);
        connection.put("jdbcUrl",urlJson);
        parameter.put("connection",connection);
        //
        reader.put("parameter",parameter);
        sb.append(reader);
        return sb.toString();
    }

    public static void main(String[] args) {

    }


}