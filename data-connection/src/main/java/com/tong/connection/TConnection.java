package com.tong.connection;

import com.tong.connection.hive.HiveConnection;
import com.tong.connection.mysql.MysqlConnection;
import com.tong.connection.odps.OdpsConnection;
import com.tong.connection.sqlserver.SqlserverConnection;
import lombok.Data;


/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: Connection
 * @time: 2019/7/23 14:27
 * @desc:
 */
@Data
public class TConnection<T> {

    T connection;

    public ConnectionResult getConnection(){
        if(connection instanceof MysqlConnection){
            return ((MysqlConnection) connection).getConnectionResult();
        }if(connection instanceof OdpsConnection){
            return ((OdpsConnection) connection).getConnectionResult();
        }if(connection instanceof HiveConnection){
            return ((HiveConnection) connection).getConnectionResult();
        }if(connection instanceof SqlserverConnection){
            return ((SqlserverConnection) connection).getConnectionResult();
        }
        return null;
    }

}