package com.tong.createtable;

import com.tong.createtable.hive.HiveTable;
import com.tong.mybatis.mapper.TableColumnInfoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: createController
 * @time: 2019/10/21 9:11
 * @desc:
 */
@RestController
@RequestMapping("create")
@Api("create相关的api")
public class createController {

    @Autowired
    private TableColumnInfoMapper tableColumnInfoMapper;

    @ApiOperation(value="创建hivesql语句", notes="创建hivesql语句")
    @RequestMapping(value = "/hiveTable", method = RequestMethod.POST)
    public String createHiveTable(String db,String tableName){
        HiveTable hiveTable = new HiveTable();
        hiveTable.setTableColumnInfos(tableColumnInfoMapper.selectColumnByTable(tableName));
        hiveTable.setTableName(tableName);
        hiveTable.setHdfsDefaultFs("hdfs://master:8020");
        hiveTable.setDB(db);
        hiveTable.setHdfsDataBasePath("/user/hive/warehouse/db_legal_person/"+tableName);
        Execute<HiveTable> execute = new Execute<>();
        execute.setTable(hiveTable);
        return execute.create();
    }
}
