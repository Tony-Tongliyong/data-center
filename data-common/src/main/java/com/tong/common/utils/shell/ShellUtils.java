package com.tong.common.utils.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: shellUtils
 * @time: 2019/1/14 9:49
 * @desc:
 */
public class ShellUtils {

    private static final Logger logger = LoggerFactory.getLogger(ShellUtils.class);

    public static Boolean execute(String command){
        InputStreamReader stdISR = null;
        InputStreamReader errISR = null;

        Boolean result = false;
        Process process = null;
        try {
            logger.info(command);
            process = Runtime.getRuntime().exec(command);

            CommandStreamGobbler errorGobbler = new CommandStreamGobbler(process.getErrorStream(), command, "ERR");
            CommandStreamGobbler outputGobbler = new CommandStreamGobbler(process.getInputStream(), command, "STD");

            errorGobbler.start();
            // 必须先等待错误输出ready再建立标准输出
            while (!errorGobbler.isReady()) {
                Thread.sleep(10);
            }
            outputGobbler.start();
            while (!outputGobbler.isReady()) {
                Thread.sleep(10);
            }

            int exitValue = process.waitFor();
            result = errorGobbler.isSuccess() && outputGobbler.isSuccess();
        } catch (IOException | InterruptedException e) {
            result = false;
            logger.error("",e);
        } finally {
            try {
                if (stdISR != null) {
                    stdISR.close();
                }
                if (errISR != null) {
                    errISR.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                logger.error("正式执行命令：" + command + "有IO异常：" + e);
            }
        }
        return result;
    }
}