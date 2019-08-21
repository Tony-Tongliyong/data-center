package com.tong.datax.drds;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: DrdsWriter
 * @time: 2019/8/20 16:16
 * @desc:
 */
@Data
public class DrdsWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "drdswriter";
    @ApiModelProperty("写入模式")
    private String writeMode = "insert";
    @ApiModelProperty("账户名称")
    private String writerUserName;
    @ApiModelProperty("账户密码")
    private String writerPassword;
    @ApiModelProperty("字段")
    private List<String> writerColumns;
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
        JSONObject connection = new JSONObject();
        connection.put("table",writerTable);
        connection.put("jdbcUrl",writerJdbcUrl);
        parameter.put("connection",connection);

        writer.put("parameter",parameter);
        return writer;
    }
}