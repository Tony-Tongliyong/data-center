package com.tong.sdk.odps;

import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
public class AclDto {

    /**
     * 表名
     */
    private String table;

    /**
     * 用户和权限
     */
    private List<AclUsers> aclUsers;

    @Data
    @ToString
    public static class AclUsers {
        /**
         * 用户名
         */
        private String userName;

        /**
         * 操作权限
         */
        private List<String> actions;
    }
}
