package com.tong.connection.mysql;

import com.tong.connection.ConnectionResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
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

    public ConnectionResult getConnection(){
        ConnectionResult connectionResult = new ConnectionResult();
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(mysqlUrl,mysqlUser,mysqlPwd);
            connectionResult.setMessage("连接mysql成功");
            connectionResult.setSuccess(1);
        } catch (ClassNotFoundException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("mysql连接异常，报错原因：无法加载驱动类");
        } catch (SQLException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("mysql连接异常，报错可能原因：mysql连接地址出错、网络异常等");
        }
        connectionResult.setConnection(connection);
        return connectionResult;
    }
}