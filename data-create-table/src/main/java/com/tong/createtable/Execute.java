package com.tong.createtable;

import com.tong.createtable.hive.HiveTable;
import lombok.Data;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: execute
 * @time: 2019/10/18 18:00
 * @desc:
 */
@Data
public class Execute<T> {

    T table;

    public String create(){
        if(table instanceof HiveTable){
            return ((HiveTable) table).createHiveTableSQL();
        }else{
            return null;
        }
    }

}