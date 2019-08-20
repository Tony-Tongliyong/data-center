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
    private String readerAccessID;
    @ApiModelProperty("连接密码")
    private String readerAccessKey;
    @ApiModelProperty("项目空间")
    private String readerProject;
    @ApiModelProperty("表名")
    private String readerTableName;
    @ApiModelProperty("表名")
    private List<String> readerColumn;
    @ApiModelProperty("分区")
    private List<String> readerPartition;
    @ApiModelProperty("odps的server地址")
    private String readerOdpsServer;
    @ApiModelProperty("odps的tunnel地址")
    private String readerOdpsTunnel;

    public JSONObject makeJson(){
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("accessId",readerAccessID);
        parameter.put("accessKey",readerAccessKey);
        parameter.put("project",readerProject);
        parameter.put("partition",readerPartition);
        parameter.put("column",readerColumn);
        parameter.put("odpsServer",readerOdpsServer);
        parameter.put("tunnerlServer",readerOdpsTunnel);
        reader.put("parameter",parameter);
        return reader;
    }




}