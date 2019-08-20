package com.tong.datax.odps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: OdpsReader
 * @time: 2019/8/14 10:40
 * @desc:
 */
@Data
public class OdpsReader {

    @ApiModelProperty("读取类型")
    private final String readerName = "odpsreader";
    @ApiModelProperty("连接账户名")
    private String accessID;
    @ApiModelProperty("连接密码")
    private String accessKey;
    @ApiModelProperty("项目空间")
    private String project;
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("表名")
    private List<String> column;
    @ApiModelProperty("分区")
    private List<String> partition;
    @ApiModelProperty("odps的server地址")
    private String odpsServer;
    @ApiModelProperty("odps的tunnel地址")
    private String odpsTunnel;

    public JSONObject makeJson(){
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("accessId",accessID);
        parameter.put("accessKey",accessKey);
        parameter.put("project",project);
        parameter.put("partition",partition);
        parameter.put("column",column);
        parameter.put("odpsServer",odpsServer);
        parameter.put("tunnerlServer",odpsTunnel);
        reader.put("parameter",parameter);
        return reader;
    }




}