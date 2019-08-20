package com.tong.connection.mysql;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: MysqlConnection
 * @time: 2019/7/24 14:01
 * @desc:
 */
@Data
public class MysqlConnection {

    @ApiModelProperty("驱动类")
    private final String driverClass = "com.mysql.jdbc.Driver";
    @ApiModelProperty("连接地址")
    private String mysqlUrl;
    @ApiModelProperty("账户名")
    private String mysqlUser;
    @ApiModelProperty("账户密码")
    private String mysqlPwd;

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(mysqlUrl,mysqlUser,mysqlPwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}