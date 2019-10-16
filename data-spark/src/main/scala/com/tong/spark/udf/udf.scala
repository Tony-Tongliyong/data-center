package com.tong.spark.udf

import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}
import com.tong.spark.reader.RdmsReader.run
/**
  * tongly
  * 18158190830 @163.com
  * AdministrativeEnforcement
  * 2019/8/30 11:32
  *
  */
object udf {

  def main(args: Array[String]): Unit = {
    val ss = SparkSession
      .builder()
      .appName("statisic")
      .master("local[8]")
      .getOrCreate()

    val user = "root"
    val password = "123456"
    val ip = "192.168.40.14"
    val port = 3306
    val dataType = "MySQL"
    val dataBase = "aquilatest"
    val table = "e_pri_person"
    val useSql = true
    val sql1 = ""

    print(sql1)

    val data = run(ss, user, password, ip, port, dataType, dataBase, table, sql1, useSql)

    val data1 = data.rdd.map(x=>{
      var cerno = x.get(1)
      var num = x.get(2)
      if(num == null){
        num = 0L
      }
      if(cerno == null){
        cerno = ""
      }
      Row(x.get(0),x.get(1),num)
    })

    val structType = StructType(Array(
      StructField("pripid", StringType, true),
      StructField("cerno", StringType, true),
      StructField("num", LongType, true)
    ))

    val bigDataDF = ss.sqlContext.createDataFrame(data1,structType)

    bigDataDF.registerTempTable("bigDataTable")

    ss.sqlContext.sql("select * from bigDataTable").show()

//    udaf
//    ss.sqlContext.udf.register("AdministrativeEnforcementUDAF",new AdministrativeEnforcementUDAF)

//    ss.sqlContext.sql("select AdministrativeEnforcementUDAF(pripid,cerno,num)  from bigDataTable) a").show()

//    udtf(基于spark1.5)
//    ss.sql("CREATE TEMPORARY FUNCTION AdministrativeEnforcementUDATF as 'com.shuzheng.aquila.statistic.administativeEnforcement.AdministrativeEnforcementUDTF'")
//
//    ss.sqlContext.sql("select AdministrativeEnforcementUDTF(pripid,cerno,num)  from bigDataTable) a").show()

//    udf
    ss.sqlContext.udf.register("AdministrativeEnforcementUDAF",getResult _)

    val data2 = ss.sqlContext.sql("select pripid,cerno,AdministrativeEnforcementUDAF(pripid,cerno,num)  from bigDataTable ").toDF()


  }

   def getResult(pripid:String,cerno:String,num:Int)= {

     var temp = 0

     var flag = false

     if(!flag){
       temp = -1
     }
     val score = calculate(num)
     score
  }

  def calculate(num:Integer):Double={
    var result :Double = 0
    if(num == 0 || num == -1){
      result = 0
    }
    if(num == 1){
      result = 250 * 0.01375
    }
    if(num == 2 || num == 3 ){
      result = 500 * 0.01375
    }
    if(num == 4 || num == 5 ){
      result = 750 * 0.01375
    }
    if(num > 5 ){
      result = 1000 * 0.01375
    }
    result
  }


}
