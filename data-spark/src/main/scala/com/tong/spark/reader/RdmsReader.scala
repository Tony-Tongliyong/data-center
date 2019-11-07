package com.tong.spark.reader

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SparkSession}

/**

  * @: 2019/6/5 3:12 PM
  *
  *              读关系型数据库
  *              关系型数据库，由关系型数据中的表转换成dataframe
  */
class RdmsReader(userParm:String, passwordParm:String,ipParm:String, portParm:Int, dataTypeParm:String,dataBaseParm:String,tableParm:String,sqlParm:String,useSqlParm:Boolean) {
  var user: String = userParm
  var password: String = passwordParm
  var ip: String = ipParm
  var port: Int = portParm
  var dataType: String = dataTypeParm
  var dataBase: String = dataBaseParm
  var table: String = tableParm
  var sql: String = sqlParm
  var useSql: Boolean = useSqlParm
}

object RdmsReader {
  /**
    * 读取数据
    * @param ss SparkSession
    * @param rdmsReader 读对象
    * @return 返回读出的数据
    */
  def reader(ss:SparkSession,rdmsReader:RdmsReader):DataFrame={
    val prop = new Properties()
    prop.setProperty("user", rdmsReader.user)
    prop.setProperty("password", rdmsReader.password)
    prop.setProperty("port", rdmsReader.port.toString)
    var url = ""

    rdmsReader.dataType match {
      case "MySQL" => prop.setProperty("driver","com.mysql.jdbc.Driver")
        url = s"jdbc:mysql://${rdmsReader.ip}:${rdmsReader.port}/${rdmsReader.dataBase}"
      case "Oracle" => prop.setProperty("driver","oracle.jdbc.driver.OracleDriver")
      // TODO
      case "SqlServer" => prop.setProperty("driver","com.microsoft.sqlserver.jdbc.SQLServerDriver")
      // TODO
      case "GBase" => prop.setProperty("driver","com.gbase.jdbc.Driver")
      // TODO
      case "Postgres" => prop.setProperty("driver","org.postgresql.Driver")
      // TODO
    }

    val sqlc = ss.sqlContext
    val comm = if (rdmsReader.useSql) sqlc.read.jdbc(url, "("+rdmsReader.sql+") TEMPTABLE", prop) else sqlc.read.jdbc(url, rdmsReader.table, prop)
    val df = comm.repartition(5)
    df.cache()
    // 输出
    df.createOrReplaceTempView(rdmsReader.table)
    sqlc.cacheTable(rdmsReader.table)
    df
  }

  /**
    * 读取并返回数据
    * @param ss SparkSession
    * @param user 用户名
    * @param password 密码
    * @param ip 数据库ip
    * @param port 数据库端口号
    * @param dataType 数据库类型
    * @param dataBase 数据库名称
    * @param table 表名
    * @param selectSql 查询数据sql
    * @param useSql 是否只用sql查询
    * @return 返回查询出来的数据
    */
  def run(ss:SparkSession,user:String,password:String,ip:String,port:Int,dataType:String,dataBase:String,table:String,selectSql:String,useSql:Boolean)={
    val rdmsReader = new RdmsReader(user,password,ip,port,dataType,dataBase,table,selectSql,useSql)
    reader(ss,rdmsReader)
  }

}

