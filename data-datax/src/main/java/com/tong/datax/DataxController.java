package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import com.tong.common.Result.ResponseResult;
import com.tong.datax.hdfs.HdfsReader;
import com.tong.datax.hdfs.HdfsWriter;
import com.tong.datax.mysql.MySqlReader;
import com.tong.datax.mysql.MySqlWriter;
import com.tong.datax.odps.OdpsReader;
import com.tong.datax.odps.OdpsWriter;
import com.tong.mybatis.mapper.TableColumnInfoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TableColumnInfoMapper tableColumnInfoMapper;
    /**
     * mysql--mysql生成JSON
     * @return
     */
    @ApiOperation(value="mysql数据导入到mysql参数配置，生成JSON字符串", notes="mysql数据导入到mysql")
    @RequestMapping(value = "/mysqlToMysqlJSON", method = RequestMethod.POST)
    public ResponseResult mysqlToMysqlJSON( MySqlReader mysqlReader,
                                            MySqlWriter mysqlWriter){
        JsonBuild<MySqlReader,MySqlWriter> jsonBulid = new JsonBuild<>();
        jsonBulid.setReader(mysqlReader);
        jsonBulid.setWriter(mysqlWriter);
        JSONObject jsonObject = jsonBulid.makeJson();
        return ResponseResult.success(jsonObject);
    }

    @ApiOperation(value="odps数据导入到odps参数配置，生成JSON字符串", notes="odps数据导入到odps")
    @RequestMapping(value = "/odpsToOdpsJSON", method = RequestMethod.POST)
    public ResponseResult odpsToOdpsJSON(@RequestBody OdpsReader odpsReader,
                                         @RequestBody OdpsWriter odpsWriter){
        JsonBuild<OdpsReader,OdpsWriter> jsonBulid = new JsonBuild<>();
        jsonBulid.setReader(odpsReader);
        jsonBulid.setWriter(odpsWriter);
        JSONObject jsonObject = jsonBulid.makeJson();
        return ResponseResult.success(jsonObject);
    }

    @ApiOperation(value="mysql数据导入到hdfs参数配置，生成JSON字符串", notes="mysql数据导入到hdfs")
    @RequestMapping(value = "/mysqlToHdfs", method = RequestMethod.POST)
    public ResponseResult mysqlToHdfs(MySqlReader mySqlReader,HdfsWriter hdfsWriter){
        JsonBuild<MySqlReader, HdfsWriter> jsonBulid = new JsonBuild<>();
        List<String> columns = new ArrayList<>();
        columns = tableColumnInfoMapper.selectColumnsByTable(mySqlReader.getReaderTable().get(0));
        mySqlReader.setReaderColumns(columns);
        List<Map<String,String>> maps = new ArrayList<>();
        for(int i=0;i<columns.size();i++){
            Map<String,String> map = new HashMap<>();
            map.put("name",columns.get(i));
            map.put("type","String");
            maps.add(map);
        }
        hdfsWriter.setWriterColumns(maps);
        jsonBulid.setReader(mySqlReader);
        jsonBulid.setWriter(hdfsWriter);
        JSONObject jsonObject = jsonBulid.makeJson();
        return ResponseResult.success(jsonObject);
    }


}