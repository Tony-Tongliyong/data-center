package com.tong.datax.hdfs;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
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
    private String readerdefaultFS;
    @ApiModelProperty("字段")
    private List<Map<String,String>> readerColumns;
    @ApiModelProperty("文件类型")
    private String readerfileType;
    @ApiModelProperty("编码格式")
    private String readerencoding;
    @ApiModelProperty("区分键")
    private String readerfieldDelimiter;
    @ApiModelProperty("是否有kerberos认证")
    private Boolean readerhaveKerberos ;
    @ApiModelProperty("keytab路径")
    private String readerkerberosKeytabFilePath;
    @ApiModelProperty("kerberos用户")
    private String readerkerberosPrincipal;

    public JSONObject makeJson(){
        //reader模块
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("path",readerPath);
        parameter.put("defaultFS",readerdefaultFS);
        parameter.put("column",readerColumns);
        parameter.put("fileType",readerfileType);
        parameter.put("encoding",readerencoding);
        parameter.put("haveKerberos",readerhaveKerberos);
        if(readerhaveKerberos){
            parameter.put("kerberosKeytabFilePath",readerkerberosKeytabFilePath);
            parameter.put("kerberosPrincipal",readerkerberosPrincipal);
        }
        parameter.put("fieldDelimiter",readerfieldDelimiter);
        reader.put("parameter",parameter);
        return reader;
    }
}