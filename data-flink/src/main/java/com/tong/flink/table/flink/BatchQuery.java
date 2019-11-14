package com.tong.flink.table.flink;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.table.api.Over;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.table.sources.TableSource;
import org.apache.flink.types.Row;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: BatchQuery
 * @time: 2019/11/12 15:46
 * @desc:
 */
public class BatchQuery {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment fbEnv = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = BatchTableEnvironment.create(fbEnv);

        String[] fieldNames = {"cerno","certype","name"};
        TypeInformation[] fieldTypes = {Types.STRING, Types.STRING, Types.STRING};
        tableEnv.registerTableSource("MySource1", new CsvTableSource("C:\\Users\\54192\\Desktop\\data\\corporate_person\\data_2.csv", fieldNames, fieldTypes));
        tableEnv.registerTableSource("MySource2", new CsvTableSource("C:\\Users\\54192\\Desktop\\data\\corporate_person\\data_3.csv", fieldNames, fieldTypes));
        tableEnv.registerTableSink("MySink1", new CsvTableSink("C:\\Users\\54192\\Desktop\\data\\corporate_person\\dataSink_2.csv").configure(fieldNames, fieldTypes));
        tableEnv.registerTableSink("MySink2", new CsvTableSink("C:\\Users\\54192\\Desktop\\data\\corporate_person\\dataSink_3.csv").configure(fieldNames, fieldTypes));

//        tableEnv.registerTableSource("MySource1", new CsvTableSource("/home/datastreaming/data_2.csv", fieldNames, fieldTypes));
//        tableEnv.registerTableSource("MySource2", new CsvTableSource("/home/datastreaming/data_4.csv", fieldNames, fieldTypes));
//        tableEnv.registerTableSink("MySink1", new CsvTableSink("/home/datastreaming/dataSink_2.csv").configure(fieldNames, fieldTypes));
//        tableEnv.registerTableSink("MySink2", new CsvTableSink("/home/datastreaming/dataSink_3.csv").configure(fieldNames, fieldTypes));
//
        Table table1 = tableEnv.scan("MySource1").where("LIKE(name, '杭州%')");
        table1.insertInto("MySink1");

        Table table2 = table1.unionAll(tableEnv.scan("MySource2"));
        table2.insertInto("MySink2");
//        Table orders = tableEnv.scan("MySource1");

//        Table revenue = orders
//                .groupBy("cerno,certype,name")
//                .select("cerno");
//        revenue.printSchema();

        tableEnv.execute("2222");
//        System.out.println(explanation);
    }
}