package com.tong.datax;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: JsonBulid
 * @time: 2019/7/12 14:39
 * @desc:
 */
@Data
public class JsonBulid {

    String dataBaseType;

    private JSONObject makeJson(){
        JSONObject json = new JSONObject();
        JSONObject job = new JSONObject();
        JSONObject setting = new JSONObject();
        JSONObject speed = new JSONObject();
        speed.put("chaneel","1");
        setting.put("speed",speed);
        JSONObject content = new JSONObject();
        content.put("reader",reader());
        content.put("writer",writer());
        job.put("setting",setting);
        json.put("job",job);
        return json;
    }

    /**
     * 读取来源
     * @return
     */
    private JSONObject reader(){
        return null;
    }

    /**
     * 写入去处
     * @return
     */
    private JSONObject writer(){
        return null;
    }


}