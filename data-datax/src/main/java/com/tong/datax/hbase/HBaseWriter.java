package com.tong.datax.hbase;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: HBaseWriter
 * @time: 2019/10/16 15:07
 * @desc:
 */
@Data
public class HBaseWriter {

    @ApiModelProperty("写入类型")
    private final  String writerName = "hbase11xwriter";
    @ApiModelProperty("写入hbase地址")
    private String writerHBaseZookeeperQuorum ;
    @ApiModelProperty("写入hbase表名")
    private String writerTableName ;
    @ApiModelProperty("写入格式")
    private String writerEncoding ;
    @ApiModelProperty("写入类型")
    private String writerMode;
    @ApiModelProperty("写入主键字段")
    private Map<String,String> writerRowKey;
    @ApiModelProperty("写入字段")
    private Map<String,String> writerColumn;

    public JSONObject makeJson(){
        JSONObject writer = new JSONObject();
        writer.put("name",writerName);
        JSONObject parameter = new JSONObject();
        JSONObject hbaseConfig = new JSONObject();
        hbaseConfig.put("hbase.zookeeper.quorum",writerHBaseZookeeperQuorum);
        parameter.put("hbaseConfig",hbaseConfig);
        parameter.put("table",writerTableName);
        parameter.put("mode",writerMode);
        parameter.put("encoding",writerEncoding);

        List<JSONObject> rowkeyColumn = new ArrayList<>();
        writerRowKey.forEach((k,v)->{
            JSONObject rowKey = new JSONObject();
            rowKey.put("index",k);
            rowKey.put("type",v);
            rowkeyColumn.add(rowKey);
        });

        List<JSONObject> columns = new ArrayList<>();
        final int[] i = {1};
        writerColumn.forEach((k,v)->{
            JSONObject column = new JSONObject();
            column.put("index", i[0]);
            column.put("name",k);
            column.put("type",v);
            columns.add(column);
            i[0]++;
        });
        parameter.put("rowkeyColumn",rowkeyColumn);
        parameter.put("column",columns);
        writer.put("parameter",parameter);
        return writer;
    }
}