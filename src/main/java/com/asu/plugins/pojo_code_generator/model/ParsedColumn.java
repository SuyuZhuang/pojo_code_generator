package com.asu.plugins.pojo_code_generator.model;

/**
 * sql中解析出的数据库列信息
 */
public class ParsedColumn {

    /**
     * 字段名 如 delete_status
     */
    private String name;

    /**
     * 字段类型 如 bigint
     */
    private String columnType;

    /**
     * 注释信息
     */
    private String comment;

    /**
     * 是否是主键
     */
    private Boolean isPrimary;


    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
