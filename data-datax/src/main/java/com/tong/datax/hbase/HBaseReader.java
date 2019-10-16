package com.tong.datax.hbase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: HBaseReader
 * @time: 2019/10/16 15:06
 * @desc:
 */
@Data
public class HBaseReader {

    @ApiModelProperty("读取类型")
    private final  String readerName = "hbase11xreader";
    @ApiModelProperty("读取hbase地址")
    private String readerHBaseZookeeperQuorum ;
    @ApiModelProperty("读取hbase表名")
    private String readerTableName ;
    @ApiModelProperty("读取格式")
    private String readerEncoding ;
    @ApiModelProperty("读取乐行")
    private String readerMode;
    @ApiModelProperty("读取主键字段")
    private Map<String,String> readerRowKey;
    @ApiModelProperty("读取字段")
    private Map<String,String> readerColumn;
    @ApiModelProperty("读取开始主键值")
    private String readerStartRowKey;
    @ApiModelProperty("读取结束主键值")
    private String readerEndRowKey;

    public JSONObject makeJson() {
        JSONObject reader = new JSONObject();
        reader.put("name",readerName);
        JSONObject parameter = new JSONObject();
        JSONObject hbaseConfig = new JSONObject();
        hbaseConfig.put("hbase.zookeeper.quorum",readerHBaseZookeeperQuorum);
        parameter.put("hbaseConfig",hbaseConfig);
        parameter.put("table",readerTableName);
        parameter.put("encoding",readerEncoding);
        parameter.put("mode",readerMode);
        List<JSONObject> columns = new ArrayList<>();
        readerRowKey.forEach((k,v)->{
            JSONObject rowKey = new JSONObject();
            rowKey.put("name",k);
            rowKey.put("type",v);
            columns.add(rowKey);
        });
        readerColumn.forEach((k,v)->{
            JSONObject column = new JSONObject();
            column.put("name",k);
            column.put("type",v);
            columns.add(column);
        });
        parameter.put("column",columns);
        JSONObject range = new JSONObject();
        range.put("startRowkey",readerStartRowKey);
        range.put("endRowkey",readerEndRowKey);
        parameter.put("range",range);
        reader.put("parameter",parameter);
        return reader;
    }
}