package com.tong.datax.odps;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: OdpsWriter
 * @time: 2019/8/14 10:39
 * @desc:
 */
@Data
public class OdpsWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "odpswriter";
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

    public JSONObject makeJson() {
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("accessId",accessID);
        parameter.put("accessKey",accessKey);
        parameter.put("project",project);
        parameter.put("partition",partition);
        parameter.put("column",column);
        parameter.put("odpsServer",odpsServer);
        parameter.put("tunnerlServer",odpsTunnel);
        parameter.put("truncate",true);
        parameter.put("accountType","aliyun");
        writer.put("parameter",parameter);
        return writer;
    }
}