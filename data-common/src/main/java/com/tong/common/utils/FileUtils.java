package com.tong.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: tongly
 * @contact:wuxin@yscredit.com
 * @file: FileUtils
 * @time: 2019/7/13 10:30
 * @desc:
 */
public class FileUtils {

    public static Boolean stringToFile(String str,String fileName){
        Boolean flag = true;
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(str);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        }
        return flag;
    }

    /**
     * 创建文件
     * @param fileName
     * @return
     */
    public static boolean makeDirectory(String fileName) {
        File file = new File(fileName);
        return makeDirectory(file);
    }

    /**
     * 创建文件父路径
     * @param file
     * @return
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }
}