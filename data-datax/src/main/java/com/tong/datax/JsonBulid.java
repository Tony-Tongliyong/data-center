package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import com.tong.datax.drds.DrdsReader;
import com.tong.datax.drds.DrdsWriter;
import com.tong.datax.hdfs.HdfsReader;
import com.tong.datax.hdfs.HdfsWriter;
import com.tong.datax.mysql.MySqlReader;
import com.tong.datax.mysql.MySqlWriter;
import com.tong.datax.odps.OdpsReader;
import com.tong.datax.odps.OdpsWriter;
import lombok.Data;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: JsonBulid
 * @time: 2019/7/12 14:39
 * @desc:
 */
@Data
public class JsonBulid<T1,T2> {

    T1 reader;

    T2 writer;

    String channel;

     JSONObject makeJson(){
        JSONObject json = new JSONObject();
        JSONObject job = new JSONObject();
        JSONObject setting = new JSONObject();
        JSONObject speed = new JSONObject();
        speed.put("channel",channel);
        setting.put("speed",speed);
        JSONObject content = new JSONObject();
        content.put("reader",reader());
        content.put("writer",writer());
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
        }else {
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
        }else {
            return null;
        }
    }


}