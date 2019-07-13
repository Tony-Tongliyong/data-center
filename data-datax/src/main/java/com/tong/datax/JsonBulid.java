package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import com.tong.datax.mysql.MySqlReader;
import com.tong.datax.mysql.MySqlWriter;
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

     JSONObject makeJson(){
        JSONObject json = new JSONObject();
        JSONObject job = new JSONObject();
        JSONObject setting = new JSONObject();
        JSONObject speed = new JSONObject();
        speed.put("channel","1");
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
     * 读取来源
     * @return
     */
    JSONObject reader(){
        if(reader instanceof MySqlReader){
            return ((MySqlReader) reader).makeJson();
        }else {
            return null;
        }
    }

    /**
     * 写入去处
     * @return
     */
    JSONObject writer(){
        if(writer instanceof MySqlWriter){
            return ((MySqlWriter) writer).makeJson();
        }else {
            return null;
        }
    }


}