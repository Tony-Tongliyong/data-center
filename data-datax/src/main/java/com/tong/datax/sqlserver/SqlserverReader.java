package com.tong.datax.sqlserver;

import com.alibaba.fastjson.JSONObject;
import com.tong.connection.sqlserver.SqlserverConnection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: SqlserverReader
 * @time: 2019/11/1 9:40
 * @desc:
 */
@Data
public class SqlserverReader {

    @ApiModelProperty("读取类型")
    private final String readerName = "sqlserverreader";
    @ApiModelProperty("连接账户名")
    private String readerUserName;
    @ApiModelProperty("连接密码")
    private String readerPassword;
    @ApiModelProperty("字段")
    private List<String> readerColumns;
    @ApiModelProperty("区分键")
    private String splitPk;
    @ApiModelProperty("读取条件")
    private String readerWhere;
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
        if(null != readerWhere) {
            parameter.put("where", readerWhere);
        }
        if (null == readerColumns || 0 ==readerColumns.size()){
            readerColumns = getColumns();
        }
        parameter.put("column",readerColumns);
        parameter.put("splitPk",splitPk);
        List<Map<String,Object>> connection = new ArrayList();
        Map<String,Object> map = new HashMap<>();
        map.put("table",readerTable);
        map.put("jdbcUrl",readerJdbcUrl);
        connection.add(map);
        parameter.put("connection",connection);

        reader.put("parameter",parameter);
        return reader;
    }

    public List<String> getColumns(){
        SqlserverConnection sqlserverConnection = new SqlserverConnection();
        sqlserverConnection.setSqlserverUrl(readerJdbcUrl.get(0));
        sqlserverConnection.setSqlserverPwd(readerPassword);
        sqlserverConnection.setSqlserverUser(readerUserName);
        Connection connection = sqlserverConnection.getConnectionResult().getConnection();
        List<String> columns = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("show full columns from "+readerTable.get(0));
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                columns.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }
}