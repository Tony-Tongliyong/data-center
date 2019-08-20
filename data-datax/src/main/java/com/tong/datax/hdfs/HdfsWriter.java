package com.tong.datax.hdfs;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: HdfsWriter
 * @time: 2019/8/20 16:34
 * @desc:
 */
@Data
public class HdfsWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "hdfswriter";
    @ApiModelProperty("写入模式")
    private String writeMode = "insert";
    @ApiModelProperty("存储地址")
    private String writerPath;
    @ApiModelProperty("文件名称")
    private String writefileName;
    @ApiModelProperty("默认hdfs地址")
    private String writedefaultFS;
    @ApiModelProperty("字段")
    private List<Map<String,String>> writerColumns;
    @ApiModelProperty("文件类型")
    private String writefileType;
    @ApiModelProperty("区分键")
    private String writefieldDelimiter;
    @ApiModelProperty("是否有kerberos认证")
    private Boolean writehaveKerberos ;
    @ApiModelProperty("keytab路径")
    private String writekerberosKeytabFilePath;
    @ApiModelProperty("kerberos用户")
    private String writekerberosPrincipal;


    public JSONObject makeJson(){
        //reader模块
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("writerMode",writeMode);
        parameter.put("path",writerPath);
        parameter.put("defaultFS",writedefaultFS);
        parameter.put("column",writerColumns);
        parameter.put("fileType",writefileType);
        parameter.put("fileName",writefileName);
        parameter.put("haveKerberos",writehaveKerberos);
        if(writehaveKerberos){
            parameter.put("kerberosKeytabFilePath",writekerberosKeytabFilePath);
            parameter.put("kerberosPrincipal",writekerberosPrincipal);
        }
        parameter.put("fieldDelimiter",writefieldDelimiter);
        writer.put("parameter",parameter);
        return writer;
    }
}