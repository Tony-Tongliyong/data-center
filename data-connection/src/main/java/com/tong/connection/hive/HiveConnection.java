package com.tong.connection.hive;

import java.sql.Connection;

import com.tong.connection.ConnectionResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: HiveConnection
 * @time: 2019/8/21 15:23
 * @desc:
 */
@Data
public class HiveConnection {

    @ApiModelProperty("驱动类")
    private final String hiveDriverClass = "org.apache.hive.jdbc.HiveDriver";
    /**
     * 例：jdbc:hive2://emr-header-1.cluster-12:10000/default;principal=hive/emr-header-1.cluster-12@EMR.12.COM
     */
    @ApiModelProperty("连接地址")
    private String hiveUrl;
    @ApiModelProperty("是否有kerberos")
    private Boolean haveKrb5;
    @ApiModelProperty("krb5File地址")
    private String krb5File;
    @ApiModelProperty("登录用户")
    private String userName;
    @ApiModelProperty("userKeytabFile地址")
    private String userKeytabFile;


    public  void authKrb5() throws IOException {
        //设置jvm启动时krb5的读取路径参数
        System.setProperty("java.security.krb5.conf", krb5File);
        //配置kerberos认证
        Configuration conf = new Configuration();
        conf.setBoolean("hadoop.security.authorization", true);
        conf.set("hadoop.security.authentication", "kerberos");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab(userName, userKeytabFile);

    }

    public ConnectionResult getConnection(){
        ConnectionResult connectionResult = new ConnectionResult();
        Connection connection =  null;
        try {
            if(haveKrb5) {
                authKrb5();
            }
            // 加载Hive JDBC驱动
            Class.forName(hiveDriverClass);
            // 获取JDBC连接
            connection = DriverManager.getConnection(hiveUrl);
            connectionResult.setSuccess(1);
            connectionResult.setMessage("连接hive成功");
        } catch (IOException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("连接hive异常，报错原因：kerberos验证登录失败");
        } catch (ClassNotFoundException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("连接hive异常，报错原因：无法加载驱动类");
        } catch (SQLException e) {
            connectionResult.setSuccess(0);
            connectionResult.setMessage("连接hive异常，报错可能原因：hive地址异常、网络异常等");
        }
        connectionResult.setConnection(connection);
        return connectionResult;
    }
}