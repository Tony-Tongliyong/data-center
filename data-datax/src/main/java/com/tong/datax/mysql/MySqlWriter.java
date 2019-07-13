package com.tong.datax.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
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
public class MySqlWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "mysqlwriter";
    @ApiModelProperty("写入模式")
    String writeMode = "insert";
    @ApiModelProperty("账户名称")
    String writerUserName;
    @ApiModelProperty("账户密码")
    String writerPassword;
    @ApiModelProperty("字段")
    String writerColumns;
    @ApiModelProperty("session值")
    final String session = "set session sql_mode='ANSI'";
    @ApiModelProperty("预执行语句")
    String preSql;
    @ApiModelProperty("表名")
    String writerTable;
    @ApiModelProperty("连接接地址")
    String writerJdbcUrl;


    public JSONObject makeJson(){
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);

        JSONObject parameter = new JSONObject();
        parameter.put("username",writerUserName);
        parameter.put("password",writerPassword);
        JSONArray array = new JSONArray();
        array.add(writerColumns);
        parameter.put("column",array);
        parameter.put("writeMode",writeMode);
        parameter.put("preSql",preSql);
        parameter.put("session",session);
        JSONObject connection = new JSONObject();
        JSONArray tableJson = new JSONArray();
        tableJson.add(writerTable);
        connection.put("table",tableJson);
        JSONArray urlJson = new JSONArray();
        urlJson.add(writerJdbcUrl);
        connection.put("jdbcUrl",urlJson);
        parameter.put("connection",connection);

        writer.put("parameter",parameter);
        return writer;
    }
}