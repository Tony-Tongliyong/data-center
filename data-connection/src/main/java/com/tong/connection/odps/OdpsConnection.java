package com.tong.connection.odps;

import java.sql.Connection;

import com.tong.connection.ConnectionResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: OdpsConnection
 * @time: 2019/8/21 15:08
 * @desc:
 */
@Data
public class OdpsConnection {

    @ApiModelProperty("驱动类")
    private final String driverClass = "com.aliyun.odps.jdbc.OdpsDriver";
    @ApiModelProperty("连接地址")
    private String odpsUrl;
    @ApiModelProperty("账户名")
    private String odpsAccessKey;
    @ApiModelProperty("账户密码")
    private String odpsAccessKeySecret;
    @ApiModelProperty("项目空间")
    private String odpsProject;

    public ConnectionResult getConnection(){
        ConnectionResult connectionResult = new ConnectionResult();
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection =  DriverManager.getConnection("jdbc:odps:"+odpsUrl+"?project="+odpsProject,odpsAccessKey,odpsAccessKeySecret);
            connectionResult.setMessage("连接odps成功");
            connectionResult.setSuccess(1);
        } catch (ClassNotFoundException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("连接odps异常,报错原因：无法加载驱动类");
        } catch (SQLException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("连接odps异常,报错可能原因：odps连接地址异常、网络异常等");
        }
//        connectionResult.setConnection(connection);
        return connectionResult;
    }

}