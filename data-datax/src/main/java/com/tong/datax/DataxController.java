package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import com.tong.common.Result.JSONResult;
import com.tong.common.Result.ResultUtils;
import com.tong.common.utils.FileUtils;
import com.tong.datax.mysql.MySqlReader;
import com.tong.datax.mysql.MySqlWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
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
    @RequestMapping(value = "mysqlToMysqlJSON/", method = RequestMethod.POST)
    public JSONResult mysqlToMysqlJSON(MySqlReader mysqlReader,
                                       MySqlWriter mysqlWriter){
        JsonBulid<MySqlReader,MySqlWriter> jsonBulid = new JsonBulid<>();
        jsonBulid.setReader(mysqlReader);
        jsonBulid.setWriter(mysqlWriter);
        JSONObject jsonObject = jsonBulid.makeJson();
        JSONResult<JSONObject> result = ResultUtils.success(jsonObject);
        return result;
    }

    /**
     * mysql -- mysql JSON生成File
     * @param jsonStr
     * @param fileName
     * @return
     */
    @ApiOperation(value="mysql数据导入到mysql参数配置，生成JSON字符串", notes="mysql数据导入到mysql")
    @RequestMapping(value = "mysqlToMysqlJSONFile/", method = RequestMethod.POST)
    public JSONResult mysqlToMysqlJSONFile(String jsonStr,String fileName){
       Boolean flag =  FileUtils.stringToFile(jsonStr,fileName);
       if(flag){
           return ResultUtils.success();
       }else{
           return ResultUtils.failure();
       }
    }

    /**
     * mysql -- mysql 执行datax迁移
     */
    public JSONResult mysqlToMysqlExecute(String fileName){
        return null;
    }
}