package com.tong.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: DownloadUtils
 * @time: 2019/8/12 16:47
 * @desc:
 */
public class DownloadUtils {


    public void  getExcel(HttpServletResponse response, List<Map<String,String>> list, String tableName) throws Exception {

        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(tableName+".xlsx", "UTF-8"));
        response.setContentType("multipart/form-data");

    }
}