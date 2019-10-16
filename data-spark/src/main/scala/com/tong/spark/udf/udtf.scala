package com.tong.spark.udf

import java.util

import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory
import org.apache.hadoop.hive.serde2.objectinspector.{ObjectInspector, ObjectInspectorFactory, StructObjectInspector}

/**
  *  tongly
  * 18158190830 @163.com
  *  AdministrativeEnforcementUDTF
  *  2019/8/30 13:38
  *
  */
class udtf extends GenericUDTF {

  override def initialize(args:Array[ObjectInspector]): StructObjectInspector = {
    if (args.length != 3) {
      throw new UDFArgumentLengthException("UserDefinedUDTF takes only one argument")
    }
    if (args(0).getCategory() != ObjectInspector.Category.PRIMITIVE) {
    }

    val fieldNames = new util.ArrayList[String]
    val fieldOIs = new util.ArrayList[ObjectInspector]

    //这里定义的是输出列默认字段名称
    fieldNames.add("pripid")
    fieldNames.add("cerno")
    fieldNames.add("score")
    //这里定义的是输出列字段类型
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector)
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector)
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector)

    ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs)
  }

  override def process(args: Array[AnyRef]): Unit = {
    var pripid = args(0).toString.split("")(0)
    var cerno = args(1).toString.split("")(0)
    var temp = 0
    val flag = true
    if(flag){
      temp = -1
    }
    val score = calculate(args(2).toString.split("")(1).toInt)
    forward(pripid,cerno,score)
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

  override def close(): Unit = {

  }
}
