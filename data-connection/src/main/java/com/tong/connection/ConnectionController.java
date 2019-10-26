package com.tong.connection;

import com.tong.common.Result.ResponseResult;
import com.tong.connection.hive.HiveConnection;
import com.tong.connection.mysql.MysqlConnection;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: ConnectionController
 * @time: 2019/7/27 17:41
 * @desc:
 */
@RestController
public class ConnectionController {

    /**
     * 连接mysql数据库
     */
    @ApiOperation(value="连接mysql数据库", notes="连接mysql数据库")
    @RequestMapping(value = "/mysqlConnect", method = RequestMethod.POST)
    public ResponseResult mysqlConnect(MysqlConnection mysqlConnection){
        TConnection<MysqlConnection> TConnection = new TConnection<>();
        TConnection.setConnection(mysqlConnection);
        ConnectionResult connectionResult = TConnection.getConnection();
        return ResponseResult.success(connectionResult);
    }

    /**
     * 连接mysql数据库
     */
    @ApiOperation(value="连接hive数据库", notes="连接hive数据库")
    @RequestMapping(value = "/hiveConnect", method = RequestMethod.POST)
    public ResponseResult hiveConnect(HiveConnection hiveConnection){
        TConnection<HiveConnection> TConnection = new TConnection<>();
        TConnection.setConnection(hiveConnection);
        ConnectionResult connectionResult = TConnection.getConnection();
        return ResponseResult.success(connectionResult);
    }
}