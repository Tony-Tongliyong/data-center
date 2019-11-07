package com.tong.createtable.hive;

import com.tong.mybatis.entity.TableColumnInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: Table
 * @time: 2019/10/18 17:51
 * @desc:
 */
@Data
public class HiveTable {

    private List<TableColumnInfo> tableColumnInfos;

    private String tableName ;

    private String hdfsDefaultFs;

    private String hdfsDataBasePath;

    private String db;

    private String fileType;

    public String createHiveTableSQL(){
        StringBuilder sql = new StringBuilder();
        StringBuilder baseColunmStr = new StringBuilder();
        String baseColunm = getHiveBaseColunmStr(tableColumnInfos);

        // 添加清洗所需的字段
        baseColunmStr.append(baseColunm);
        baseColunmStr.append(", `hash_unique` string COMMENT '业务主键MD5值（清洗增加）'");

        sql.append("create table if not exists ");
        sql.append(db);
        sql.append(".");
        sql.append(tableName);
        sql.append(" (");
        sql.append(baseColunmStr);
        sql.append(" )  ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\t' ");
        sql.append(" STORED AS  ");
        sql.append(fileType);
        sql.append(" LOCATION '");
        sql.append(hdfsDefaultFs);
        sql.append(hdfsDataBasePath);
        sql.append("/");
        sql.append(db);
        sql.append("/");
        sql.append(tableName);
        sql.append("' ");
        return sql.toString();
    }

    public static String getHiveBaseColunmStr(List<TableColumnInfo> columnInfos){
        StringBuilder columnBuffer = new StringBuilder();
        for (TableColumnInfo tableColumnInfo:columnInfos) {
            String field = tableColumnInfo.getColumnName();
            String type = tableColumnInfo.getColumnType();
            String comment = tableColumnInfo.getColumnComment();
            columnBuffer.append(" `");
            columnBuffer.append(field);
            columnBuffer.append("` string COMMENT '");
            columnBuffer.append(comment);
            columnBuffer.append("',\n");
        }
        columnBuffer.delete(columnBuffer.length()-2,columnBuffer.length());
        return columnBuffer.toString();
    }
}