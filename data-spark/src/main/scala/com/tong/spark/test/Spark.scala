package com.tong.spark.test

import org.apache.spark.sql.SparkSession

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: Spark
 * @time: 2019/11/7 10:00
 * @desc:
 */
object Spark {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark Statistic")
      .master("local[2]")
      .getOrCreate()
    val reader  = spark.read.format("jdbc")

    reader.option("url","jdbc:mysql://192.168.40.14:3306/clean_quzhou?characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull")

    reader.option("user","root")

    reader.option("password","123456")

    reader.option("driver","com.mysql.jdbc.Driver")

    reader.option("dbtable","base_role")

    val DF = reader.load()

    val columns = DF.columns

    for(column <- columns) {
      println(column)
    }

    var baseRoles = DF.where(s"role_name = 'admin'")

    baseRoles.rdd.map(x=>{

    })

    for(baseRole <- baseRoles){
      println(baseRole)
    }

    baseRoles = DF.filter(s"id=2")

    for(baseRole <- baseRoles){
      println(baseRole)
    }

    baseRoles = DF.select("id").where(s"role_name = 'admin'")

    for(baseRole <- baseRoles){
      println(baseRole)
    }

  }
}
