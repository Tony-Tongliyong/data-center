package com.tong.sdk.odps;

import com.aliyun.odps.type.TypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OdpsColumnType {
    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段类型
     */
    private TypeInfo columnType;

}
