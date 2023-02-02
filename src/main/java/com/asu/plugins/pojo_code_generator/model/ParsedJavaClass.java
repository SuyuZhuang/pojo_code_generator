package com.asu.plugins.pojo_code_generator.model;

import java.util.List;

/**
 * 从sql中解析出的数据库信息
 */
public class ParsedJavaClass {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段信息
     */
    private List<ParsedColumn> parsedColumnList;

    /**
     * 主键
     */
    private ParsedColumn primaryKey;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ParsedColumn> getParsedColumnList() {
        return parsedColumnList;
    }

    public void setParsedColumnList(List<ParsedColumn> parsedColumnList) {
        this.parsedColumnList = parsedColumnList;
    }

    public ParsedColumn getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ParsedColumn primaryKey) {
        this.primaryKey = primaryKey;
    }
}
