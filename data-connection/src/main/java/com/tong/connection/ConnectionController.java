package com.tong.connection;

import com.tong.common.Result.JSONResult;
import com.tong.common.Result.ResultUtils;
import com.tong.connection.mysql.MysqlConnection;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: ConnectionController
 * @time: 2019/7/27 17:41
 * @desc:
 */
@Controller
public class ConnectionController {

    /**
     * 连接mysql数据库
     */
    @ApiOperation(value="连接mysql数据库", notes="连接mysql数据库")
    @RequestMapping(value = "mysqlConnect/", method = RequestMethod.POST)
    public JSONResult mysqlConnect(MysqlConnection mysqlConnection){
        Connection<MysqlConnection> connection = new Connection<>();
        Boolean flag = connection.connectStatus();
        return ResultUtils.result(flag);
    }
}