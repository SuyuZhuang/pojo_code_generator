package com.asu.plugins.pojo_code_generator.utils;


import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.asu.plugins.pojo_code_generator.model.ParsedColumn;
import com.asu.plugins.pojo_code_generator.model.ParsedJavaClass;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class DruidSqlParserUtil {

    public static ParsedJavaClass parse(String sql) {
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql 语句不能为空");
        }

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement statement = parser.parseStatement();
        if (!(statement instanceof MySqlCreateTableStatement)) {
            throw new RuntimeException("sql 语句不是 create table 语句");
        }
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        List<SQLTableElement> tableElementList = ((MySqlCreateTableStatement) statement).getTableElementList();

        ParsedJavaClass parsedJavaClass = new ParsedJavaClass();
        List<ParsedColumn> parsedColumnList = new ArrayList<>();
        List<TableStat.Column> columns = new ArrayList<>(visitor.getColumns());
        for (int i = 0; i < columns.size(); i++) {
            ParsedColumn parsedColumn = new ParsedColumn();
            TableStat.Column column = columns.get(i);
            parsedColumn.setName(StrUtils.parseName(column.getName(), '`'));
            parsedColumn.setColumnType(column.getDataType());
            SQLTableElement sqlTableElement = tableElementList.get(i);
            SQLColumnDefinition sqlColumnDefinition = (SQLColumnDefinition) sqlTableElement;
            if (sqlColumnDefinition.getComment() != null) {
                String comment = sqlColumnDefinition.getComment().toString();
                parsedColumn.setComment(StrUtils.parseName(comment, '\''));
            }
            parsedColumn.setPrimary(false);
            if (column.isPrimaryKey()) {
                parsedColumn.setPrimary(true);
                parsedJavaClass.setPrimaryKey(parsedColumn);
            }
            parsedColumnList.add(parsedColumn);
        }


        String tableName = ((MySqlCreateTableStatement) statement).getTableName();
        tableName = StringUtils.uncapitalize(tableName);
        parsedJavaClass.setTableName(tableName);
        parsedJavaClass.setParsedColumnList(parsedColumnList);
        return parsedJavaClass;
    }



}
