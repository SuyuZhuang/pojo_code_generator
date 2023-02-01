package com.asu.plugins.pojo_code_generator.model;

import java.util.Objects;

public enum SqlColumnTypeEnum {
    TINYINT("tinyint", "java.lang.Integer"),
    BIGINT("bigint", "java.lang.Long"),
    DATETIME("datetime", "java.util.Date"),
    VARCHAR("varchar", "java.lang.String"),
    JSON("json", "java.lang.String"),
    UNKNOWN("unknown", "unknown"),
    ;

    SqlColumnTypeEnum(String sqlType, String javaType) {
        this.sqlType = sqlType;
        this.javaType = javaType;
    }

    private String sqlType;
    private String javaType;

    public static SqlColumnTypeEnum valueOfSqlType(String sqlType) {
        for (SqlColumnTypeEnum obj : SqlColumnTypeEnum.values()) {
            if (Objects.equals(obj.sqlType, sqlType)) {
                return obj;
            }
        }
        return UNKNOWN;
    }


    public String getSqlType() {
        return sqlType;
    }

    public String getJavaType() {
        return javaType;
    }
}
