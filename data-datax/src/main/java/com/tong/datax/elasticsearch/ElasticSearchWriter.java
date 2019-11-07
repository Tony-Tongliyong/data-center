package com.tong.datax.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: ElasticSearchWriter
 * @time: 2019/11/7 14:47
 * @desc:
 */
@Data
public class ElasticSearchWriter {

    @ApiModelProperty("写入类型")
    private final String writerName = "elasticsearchwriter";
    @ApiModelProperty("连接账户名")
    private String writerAccessID;
    @ApiModelProperty("连接密码")
    private String writerAccessKey;
    @ApiModelProperty("连接地址")
    private String writerEndPoint;
    @ApiModelProperty("写入索引")
    private String writerIndex;
    @ApiModelProperty("写入方式")
    private String writerType;
    @ApiModelProperty("是否清楚原有数据")
    private Boolean writerCleanUp;
    @ApiModelProperty("写入设置")
    private String writersetting;
    @ApiModelProperty("启用节点发现将(轮询)并定期更新客户机中的服务器列表")
    private String writerDiscovery;
    @ApiModelProperty("每次写入大小")
    private Long writerBatchSize;
    @ApiModelProperty("分隔符")
    private String writerSplitter ;
    @ApiModelProperty("写入字段")
    private List<Map<String,String>> writerColumns;

    public JSONObject makeJson(){
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);
        //parameter模块
        JSONObject parameter = new JSONObject();
        parameter.put("accessId",writerAccessID);
        parameter.put("accessKey",writerAccessKey);
        parameter.put("endpoint",writerEndPoint);
        parameter.put("index",writerIndex);
        parameter.put("type",writerType);
        parameter.put("cleanup",writerCleanUp);
        parameter.put("setting",writersetting);
        parameter.put("discovery",writerDiscovery);
        parameter.put("batchsize",writerBatchSize);
        parameter.put("splitter",writerSplitter);
        parameter.put("column",writerColumns);
        writer.put("parameter",parameter);
        return writer;
    }
}