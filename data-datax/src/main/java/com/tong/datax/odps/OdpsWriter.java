package com.tong.datax.odps;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: OdpsWriter
 * @time: 2019/8/14 10:39
 * @desc:
 */
@Data
public class OdpsWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "odpswriter";
    @ApiModelProperty("连接账户名")
    private String writerAccessID;
    @ApiModelProperty("连接密码")
    private String writerAccessKey;
    @ApiModelProperty("项目空间")
    private String writerProject;
    @ApiModelProperty("表名")
    private String writerTableName;
    @ApiModelProperty("表名")
    private List<String> writerColumn;
    @ApiModelProperty("分区")
    private List<String> writerPartition;
    @ApiModelProperty("odps的server地址")
    private String writerOdpsServer;
    @ApiModelProperty("odps的tunnel地址")
    private String writerOdpsTunnel;
    @ApiModelProperty("失败后是否清除")
    private Boolean writerTruncate = true;
    @ApiModelProperty("阿里云账户")
    private String writerAccount;


    public JSONObject makeJson() {
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("accessId",writerAccessID);
        parameter.put("accessKey",writerAccessKey);
        parameter.put("project",writerProject);
        parameter.put("partition",writerPartition);
        parameter.put("column",writerColumn);
        parameter.put("odpsServer",writerOdpsServer);
        parameter.put("tunnerlServer",writerOdpsTunnel);
        parameter.put("truncate",writerTruncate);
        parameter.put("accountType",writerAccount);
        writer.put("parameter",parameter);
        return writer;
    }
}