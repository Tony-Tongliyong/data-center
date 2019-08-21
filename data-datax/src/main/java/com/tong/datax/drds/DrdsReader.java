package com.tong.datax.drds;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: DrdsReader
 * @time: 2019/8/20 16:16
 * @desc:
 */
@Data
public class DrdsReader {

    @ApiModelProperty("读取类型")
    private final String readerName = "drdsreader";
    @ApiModelProperty("连接账户名")
    private String readerUserName;
    @ApiModelProperty("连接密码")
    private String readerPassword;
    @ApiModelProperty("字段")
    private List<String> readerColumns;
    @ApiModelProperty("区分键")
    private String splitPk;
    @ApiModelProperty("读取表名")
    private List<String> readerTable;
    @ApiModelProperty("连接地址")
    private List<String> readerJdbcUrl;

    public JSONObject makeJson(){
        //reader模块
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("username",readerUserName);
        parameter.put("password",readerPassword);
        parameter.put("column",readerColumns);
        JSONObject connection = new JSONObject();
        connection.put("table",readerTable);
        connection.put("jdbcUrl",readerJdbcUrl);
        parameter.put("connection",connection);
        reader.put("parameter",parameter);
        return reader;
    }
}