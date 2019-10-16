package com.tong.spark.udf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
  * tongly
  * 18158190830 @163.com
  * AdministrativeEnforcementUDAF
  * 2019/8/30 11:51
  *
  */
class udaf extends UserDefinedAggregateFunction {


   override def inputSchema: StructType = StructType(
     Array(StructField("pripid", StringType, true),
       StructField("cerno", StringType, true),
       StructField("num", IntegerType, true)
     ))

  override def bufferSchema: StructType = StructType(Array(StructField("pripid", StringType, true),
    StructField("cerno", StringType, true),
    StructField("num", IntegerType, true)
  ))


  override def dataType:  StructType = StructType(
    Array(StructField("pripid", StringType, true),
      StructField("cerno", StringType, true),
      StructField("score", DoubleType, true)
    ))

  override def deterministic: Boolean = true



  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = ""
    buffer(1) = ""
    buffer(2) = 0
  }



  override def update(buffer: MutableAggregationBuffer, input: Row): Unit ={
    buffer(0) = input.getAs[String](0)
    buffer(1) = input.getAs[String](1)
    buffer(2) = input.getAs[Integer](2)
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

  override def evaluate(buffer: Row): Any = {
    val pripid = buffer.getAs[String](0)
    val cerno = buffer.getAs[String](1)
    var temp = 0
    val flag = false
    if(flag){
      temp = -1
    }
    val score = calculate(buffer.getAs[Integer](2))
    println(pripid,cerno,score)
    Row(pripid,cerno,score)
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getAs[String](0) + buffer2.getAs[String](0)
    buffer1(1) = buffer1.getAs[String](1) + buffer2.getAs[String](1)
    buffer1(2) = buffer1.getAs[Int](2) + buffer2.getAs[Int](2)
  }
}
