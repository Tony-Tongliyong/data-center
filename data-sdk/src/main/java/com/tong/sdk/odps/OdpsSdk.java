package com.tong.sdk.odps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.type.TypeInfo;
import com.tong.common.utils.secret.RetryUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import com.aliyun.odps.security.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("Duplicates")
@AllArgsConstructor
public class OdpsSdk {
    private static final Logger log = LoggerFactory.getLogger(OdpsSdk.class);
    private String project;
    private String accessId;
    private String accessKey;
    private String endPoint;

    /**
     * 获取odps对象
     *
     * @return
     */
    private Odps getOdps() {
        Account account = new AliyunAccount(accessId, accessKey);
        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        odps.setDefaultProject(project);
        return odps;
    }

    /**
     * 获取ODPS安全管理对象
     *
     * @param project
     * @return
     * @throws OdpsException
     */
    private SecurityManager getSecurityManager(String project) throws OdpsException {
        Odps odps = getOdps();
        return odps.projects().get(project).getSecurityManager();
    }

    /**
     * 查询odps中表已授权的信息
     *
     * @param odpsTable
     * @return
     */
    public AclDto showAclForTable(String odpsTable) {
        AclDto aclDto = new AclDto();
        try {
            SecurityManager securityManager = getSecurityManager(project);
            String command = String.format("show acl for %s", odpsTable);
            String res = securityManager.runQuery(command, true);
            HashMap hashMap = JSONObject.parseObject(res, HashMap.class);
            HashMap acl = JSONObject.parseObject(hashMap.get("ACL").toString(), HashMap.class);
            aclDto.setTable(odpsTable);
            ArrayList<AclDto.AclUsers> allaclUsers = new ArrayList<>();
            acl.forEach((k, v) -> {
                AclDto.AclUsers aclUsers = new AclDto.AclUsers();
                String userName = k.toString().replaceFirst("user/", "");
                List actions = JSONArray.parseObject(v.toString(), List.class);
                aclUsers.setUserName(userName);
                aclUsers.setActions(actions);
                allaclUsers.add(aclUsers);
            });
            aclDto.setAclUsers(allaclUsers);

        } catch (Exception e) {
            log.error("获取[{}]表的权限信息失败：", odpsTable, e);
        }
        return aclDto;
    }

    /**
     * odps表授权
     *
     * @param aclDto 授权信息
     */
    public boolean grantAclForTable(AclDto aclDto) {
        AtomicBoolean grantStatus = new AtomicBoolean(false);
        String odpsTable = aclDto.getTable();
        List<AclDto.AclUsers> allaclUsers = aclDto.getAclUsers();
        try {
            SecurityManager securityManager = getSecurityManager(project);
            if (null != allaclUsers && allaclUsers.size() > 0) {
                allaclUsers.forEach(x -> {
                    String userName = x.getUserName();
                    List<String> actions = x.getActions();
                    String join = String.join(",", actions);

                    String grantSql = String.format("grant %s on table %s to user %s", join, odpsTable, userName);
                    try {
                        String s = securityManager.runQuery(grantSql, false);
                        grantStatus.set(true);
                    } catch (OdpsException e) {
                        log.error("授权失败：[{}],{}", grantSql, e);
                        grantStatus.set(false);
                    }
                });
            }else {
                log.warn("[{}]项目空间[{}]表无授权信息",project,odpsTable);
                grantStatus.set(true);
            }
        } catch (Exception e) {
            log.error("[{}]项目空间[{}]表授权失败：", project,odpsTable, e);
            grantStatus.set(false);
        }
        return grantStatus.get();
    }

    /**
     * 查看用户权限
     *
     * @param userName
     */
    public void showGrantForUser(String userName) {
        try {
            SecurityManager securityManager = getSecurityManager(project);
            String command = String.format("show grants for %s", userName);
            String res = securityManager.runQuery(command, true);

            for (String policy : res.split("\n")) {
                System.out.println(policy);
            }

        } catch (Exception e) {
            log.error("获取{}用户的权限信息失败：", userName, e);
        }
    }

    /**
     * 查看odps表是否存在
     *
     * @param odpsTable
     * @return
     */
    public boolean tableIsExists(String odpsTable) {
        Odps odps = getOdps();
        boolean exists = false;
        try {
            exists = odps.tables().exists(project, odpsTable);
        } catch (OdpsException e) {
            log.error("获取[{}]表是否存在信息失败：", odpsTable, e);
        }
        return exists;
    }

    /**
     * 获取odps表信息
     *
     * @param tableName
     * @return
     */
    public Table getTable(String tableName) {
        Odps odps = getOdps();
        final Table table = odps.tables().get(project, tableName);
        try {
            // 通过这种方式检查表是否存在，失败重试。重试策略：每秒钟重试一次，最大重试3次
            return RetryUtil.executeWithRetry(() -> {
                table.reload();
                return table;
            }, 3, 1000, false);
        } catch (Exception e) {
            log.error("获取[{}]表信息失败：", table, e);
        }
        return table;
    }

    /**
     * 创建odps表
     *
     * @param tableName    表名
     * @param schema       表schema信息
     * @param tableComment 表备注
     * @return
     */
    public boolean createTable(String tableName, TableSchema schema, String tableComment) {
        boolean task;
        Odps odps = getOdps();
        try {
            RetryUtil.executeWithRetry(() -> {
                odps.tables().create(project, tableName, schema, tableComment, false);
                return odps.tables();
            }, 3, 1000, false);
            // odps.tables().create(tableName, schema);
            task = true;
        } catch (Exception e) {
            log.error("创建[{}]表失败：", tableName, e);
            task = false;
        }
        return task;
    }

    /**
     * 删除odps表
     *
     * @param tableName 表名
     * @return
     */
    public boolean dropTable(String tableName) {
        boolean task;
        Odps odps = getOdps();
        try {
            RetryUtil.executeWithRetry(() -> {
                odps.tables().delete(project, tableName);
                return odps.tables();
            }, 3, 1000, false);
            task = true;
        } catch (Exception e) {
            log.error("删除[{}]表失败：", tableName, e);
            task = false;
        }
        return task;
    }


    /**
     * 获取odps表字段信息
     *
     * @param schema
     * @return
     */
    public List<String> getAllColumns(TableSchema schema) {
        if (null == schema) {
            throw new IllegalArgumentException("parameter schema can not be null.");
        }

        List<String> allColumns = new ArrayList<String>();

        List<Column> columns = schema.getColumns();
        OdpsType type;
        for (Column column : columns) {
            allColumns.add(column.getName());
            type = column.getType();
        }
        return allColumns;
    }

    /**
     * 获取odps表字段信息
     *
     * @param schema
     * @return
     */
    public List<OdpsColumnType> getAllColumnTypes(TableSchema schema) {
        if (null == schema) {
            throw new IllegalArgumentException("parameter schema can not be null.");
        }

        List<OdpsColumnType> allColumnTypes = new ArrayList<>();

        List<Column> columns = schema.getColumns();
        TypeInfo type;
        for (Column column : columns) {
            OdpsColumnType odpsColumnType = new OdpsColumnType();
            odpsColumnType.setColumnName(column.getName());
            odpsColumnType.setColumnType(column.getTypeInfo());
            allColumnTypes.add(odpsColumnType);
        }
        return allColumnTypes;
    }

    /**
     * 获取odps表字段类型
     *
     * @param schema
     * @return
     */
    public List<TypeInfo> getTableColumnTypeList(TableSchema schema) {
        List<TypeInfo> tableOriginalColumnTypeList = new ArrayList<>();

        List<Column> columns = schema.getColumns();
        for (Column column : columns) {
            tableOriginalColumnTypeList.add(column.getTypeInfo());
        }

        return tableOriginalColumnTypeList;
    }

    /**
     * 执行odp ssql
     *
     * @param odps
     * @param query
     * @return
     */
    public boolean runSqlTask(Odps odps, String query) {
        boolean task = false;
        if (StringUtils.isBlank(query)) {
            return true;
        }

        String taskName = "datax_odpswriter_trunacte_" + UUID.randomUUID().toString().replace('-', '_');

        Instance instance;
        Instance.TaskStatus status;
        HashMap<String, String> property = new HashMap<String, String>();
        property.put("odps.sql.type.system.odps2", "true");
        SQLTask.setDefaultHints(property);
        try {
            instance = SQLTask.run(odps, odps.getDefaultProject(), query, taskName, null, null);
            instance.waitForSuccess();
            status = instance.getTaskStatus().get(taskName);
            if (!Instance.TaskStatus.Status.SUCCESS.equals(status.getStatus())) {
                log.error("执行ODPS SQL 失败，返回值为:%s.SQL 内容为:[\n%s\n]", instance.getTaskResults().get(taskName), query);
                task = false;
            }
        } catch (OdpsException e) {
            log.error("ODPS 在运行 ODPS SQL 时抛出异常. SQL 内容为:[\n%s\n].", query);
            task = false;
        }
        return task;
    }

}
