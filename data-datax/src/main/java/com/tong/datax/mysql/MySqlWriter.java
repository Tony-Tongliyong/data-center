package com.tong.datax.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: Writer
 * @time: 2019/7/12 14:17
 * @desc:
 */
@Data
public class MySqlWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "mysqlwriter";
    @ApiModelProperty("写入模式")
    private String writeMode = "insert";
    @ApiModelProperty("账户名称")
    private String writerUserName;
    @ApiModelProperty("账户密码")
    private String writerPassword;
    @ApiModelProperty("字段")
    private List<String> writerColumns;
    @ApiModelProperty("session值")
    private List<String> session = new ArrayList<>();
    @ApiModelProperty("预执行语句")
    private List<String> preSql;
    @ApiModelProperty("表名")
    private List<String> writerTable;
    @ApiModelProperty("连接地址")
    private List<String> writerJdbcUrl;


    public JSONObject makeJson(){
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);

        JSONObject parameter = new JSONObject();
        parameter.put("username",writerUserName);
        parameter.put("password",writerPassword);
        parameter.put("column",writerColumns);
        parameter.put("writeMode",writeMode);
        parameter.put("preSql",preSql);
        session.add("set session sql_mode='ANSI'");
        parameter.put("session",session);
        JSONObject connection = new JSONObject();
        connection.put("table",writerTable);
        connection.put("jdbcUrl",writerJdbcUrl);
        parameter.put("connection",connection);

        writer.put("parameter",parameter);
        return writer;
    }
}