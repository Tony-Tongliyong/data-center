package com.tong.datax.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: Reader
 * @time: 2019/7/12 14:17
 * @desc:
 */
@Data
public class MySqlReader {

    @ApiModelProperty("读取类型")
    private final String readerName = "mysqlreader";
    @ApiModelProperty("连接账户名")
    String readerUserName;
    @ApiModelProperty("连接密码")
    String readerPassword;
    @ApiModelProperty("字段")
    String readerColumns;
    @ApiModelProperty("区分键")
    String splitPk;
    @ApiModelProperty("读取表名")
    String readerTable;
    @ApiModelProperty("连接地址")
    String readerJdbcUrl;


    public JSONObject makeJson(){
        //reader模块
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("username",readerUserName);
        parameter.put("password",readerPassword);
        JSONArray array = new JSONArray();
        array.add(readerColumns);
        parameter.put("column",array);
        parameter.put("splitPk",splitPk);
        JSONObject connection = new JSONObject();
        JSONArray tableJson = new JSONArray();
        tableJson.add(readerTable);
        connection.put("table",tableJson);
        JSONArray urlJson = new JSONArray();
        urlJson.add(readerJdbcUrl);
        connection.put("jdbcUrl",urlJson);
        parameter.put("connection",connection);

        reader.put("parameter",parameter);
        return reader;
    }

    public static void main(String[] args) {

    }


}