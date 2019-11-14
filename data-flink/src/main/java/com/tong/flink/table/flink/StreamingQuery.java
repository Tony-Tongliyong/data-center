package com.tong.flink.table.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;


/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: StreamingQuery
 * @time: 2019/11/12 15:44
 * @desc:
 */
public class StreamingQuery {
    public static void main(String[] args) {

        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
    }
}