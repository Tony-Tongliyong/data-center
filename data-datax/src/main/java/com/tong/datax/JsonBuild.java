package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import com.tong.datax.drds.DrdsReader;
import com.tong.datax.drds.DrdsWriter;
import com.tong.datax.elasticsearch.ElasticSearchWriter;
import com.tong.datax.hbase.HBaseReader;
import com.tong.datax.hbase.HBaseWriter;
import com.tong.datax.hdfs.HdfsReader;
import com.tong.datax.hdfs.HdfsWriter;
import com.tong.datax.mysql.MySqlReader;
import com.tong.datax.mysql.MySqlWriter;
import com.tong.datax.odps.OdpsReader;
import com.tong.datax.odps.OdpsWriter;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: JsonBulid
 * @time: 2019/7/12 14:39
 * @desc:
 */
@Data
public class JsonBuild<T1,T2> {

    T1 reader;

    T2 writer;

    String channel;

    /**
     * 生成json模块
     * @return
     */
    JSONObject makeJson(){
        JSONObject json = new JSONObject();
        JSONObject job = new JSONObject();
        JSONObject setting = new JSONObject();
        JSONObject speed = new JSONObject();
        speed.put("channel",channel);
        setting.put("speed",speed);
        List<Map<String,Object>> content = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("reader",reader());
        map.put("writer",writer());
        content.add(map);
        job.put("setting",setting);
        job.put("content",content);
        json.put("job",job);
        return json;
    }

    /**
     * 读取来源(mysql、odps、drds、hdfs)
     * @return
     */
    JSONObject reader(){
        if(reader instanceof MySqlReader){
            return ((MySqlReader) reader).makeJson();
        }if(reader instanceof OdpsReader){
            return ((OdpsReader) reader).makeJson();
        }if(reader instanceof DrdsReader){
            return ((DrdsReader) reader).makeJson();
        }if(reader instanceof HdfsReader){
            return ((HdfsReader) reader).makeJson();
        }if(reader instanceof HBaseReader){
            return ((HBaseReader) reader).makeJson();
        } else{
            return null;
        }
    }

    /**
     * 写入去处(mysql、odps、drds、hdfs)
     * @return
     */
    JSONObject writer(){
        if(writer instanceof MySqlWriter){
            return ((MySqlWriter) writer).makeJson();
        }if(writer instanceof OdpsWriter){
            return ((OdpsWriter) writer).makeJson();
        }if(writer instanceof DrdsWriter){
            return ((DrdsWriter) writer).makeJson();
        }if(writer instanceof HdfsWriter){
            return ((HdfsWriter) writer).makeJson();
        }if(writer instanceof HBaseWriter){
            return ((HBaseWriter) writer).makeJson();
        }if(writer instanceof ElasticSearchWriter){
            return ((ElasticSearchWriter) writer).makeJson();
        } else{
            return null;
        }
    }


}