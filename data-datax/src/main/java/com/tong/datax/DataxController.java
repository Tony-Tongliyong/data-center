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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
@Slf4j
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

    @PostMapping("/download")
    public void download(HttpServletRequest request,HttpServletResponse response, String name, String content) throws IOException {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        // 下载文件的默认名称
        String agent = request.getHeader("User-agent");
        log.info(agent);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(name + ".txt", "utf-8"));
        OutputStream out = response.getOutputStream();
        try {
            byte[] bytes = content.getBytes();
            for(int i=0;i<bytes.length;i++) {
                out.write(bytes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //此处需要关闭 wb 变量
            out.close();
        }
    }

}