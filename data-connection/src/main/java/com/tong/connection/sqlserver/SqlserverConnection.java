package com.tong.connection.sqlserver;

import com.tong.connection.ConnectionResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: SqlserverConnection
 * @time: 2019/11/1 9:50
 * @desc:
 */
@Data
public class SqlserverConnection {

    @ApiModelProperty("驱动类")
    private final String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    @ApiModelProperty("连接地址")
    private String sqlserverUrl;
    @ApiModelProperty("账户名")
    private String sqlserverUser;
    @ApiModelProperty("账户密码")
    private String sqlserverPwd;

    public ConnectionResult getConnectionResult(){
        ConnectionResult connectionResult = new ConnectionResult();
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(sqlserverUrl,sqlserverUser,sqlserverPwd);
            connectionResult.setMessage("连接sqlserver成功");
            connectionResult.setSuccess(1);
        } catch (ClassNotFoundException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("sqlserver连接异常，报错原因：无法加载驱动类");
        } catch (SQLException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("sqlserver连接异常，报错可能原因：sqlserver连接地址出错、网络异常等:"+e);
        }
        connectionResult.setConnection(connection);
        return connectionResult;
    }
}