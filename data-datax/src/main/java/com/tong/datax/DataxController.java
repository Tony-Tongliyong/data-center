package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import com.tong.common.Result.ResponseResult;
import com.tong.datax.mysql.MySqlReader;
import com.tong.datax.mysql.MySqlWriter;
import com.tong.datax.odps.OdpsReader;
import com.tong.datax.odps.OdpsWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: DataxController
 * @time: 2019/7/12 16:40
 * @desc:
 */
@RestController
@RequestMapping("datax")
@Api("DataxController相关的api")
public class DataxController {

    /**
     * mysql--mysql生成JSON
     * @return
     */
    @ApiOperation(value="mysql数据导入到mysql参数配置，生成JSON字符串", notes="mysql数据导入到mysql")
    @RequestMapping(value = "/mysqlToMysqlJSON", method = RequestMethod.POST)
    public ResponseResult mysqlToMysqlJSON(@RequestBody MySqlReader mysqlReader,
                                           @RequestBody MySqlWriter mysqlWriter){
        JsonBulid<MySqlReader,MySqlWriter> jsonBulid = new JsonBulid<>();
        jsonBulid.setReader(mysqlReader);
        jsonBulid.setWriter(mysqlWriter);
        JSONObject jsonObject = jsonBulid.makeJson();
        return ResponseResult.success(jsonObject);
    }

    @ApiOperation(value="odps数据导入到odps参数配置，生成JSON字符串", notes="odps数据导入到odps")
    @RequestMapping(value = "/odpsToOdpsJSON", method = RequestMethod.POST)
    public ResponseResult odpsToOdpsJSON(@RequestBody OdpsReader odpsReader,
                                         @RequestBody OdpsWriter odpsWriter){
        JsonBulid<OdpsReader,OdpsWriter> jsonBulid = new JsonBulid<>();
        jsonBulid.setReader(odpsReader);
        jsonBulid.setWriter(odpsWriter);
        JSONObject jsonObject = jsonBulid.makeJson();
        return ResponseResult.success(jsonObject);
    }


}