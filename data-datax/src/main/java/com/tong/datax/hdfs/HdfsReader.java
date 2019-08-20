package com.tong.datax.hdfs;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: HdfsReader
 * @time: 2019/8/20 16:34
 * @desc:
 */
@Data
public class HdfsReader {


    @ApiModelProperty("读取类型")
    private final String readerName = "hdfsreader";
    @ApiModelProperty("存储地址")
    private String readerPath;
    @ApiModelProperty("默认hdfs地址")
    private String defaultFS;
    @ApiModelProperty("字段")
    private List<Map<String,String>> readerColumns;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("编码格式")
    private String encoding;
    @ApiModelProperty("区分键")
    private String fieldDelimiter;
    @ApiModelProperty("是否有kerberos认证")
    private Boolean haveKerberos ;
    @ApiModelProperty("keytab路径")
    private String kerberosKeytabFilePath;
    @ApiModelProperty("kerberos用户")
    private String kerberosPrincipal;

    public JSONObject makeJson(){
        //reader模块
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("path",readerPath);
        parameter.put("defaultFS",defaultFS);
        parameter.put("column",readerColumns);
        parameter.put("fileType",fileType);
        parameter.put("encoding",encoding);
        parameter.put("haveKerberos",haveKerberos);
        if(haveKerberos){
            parameter.put("kerberosKeytabFilePath",kerberosKeytabFilePath);
            parameter.put("kerberosPrincipal",kerberosPrincipal);
        }
        parameter.put("fieldDelimiter",fieldDelimiter);
        reader.put("parameter",parameter);
        return reader;
    }
}