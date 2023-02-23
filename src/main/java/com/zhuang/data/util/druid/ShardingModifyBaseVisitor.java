package com.zhuang.data.util.druid;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;

import java.util.List;

public class ShardingModifyBaseVisitor {

    private boolean hasModify = false;
    private String dbType;
    private List<ShardingModifyBaseVisitor.TableInfo> tableInfoList;

    public ShardingModifyBaseVisitor(List<ShardingModifyBaseVisitor.TableInfo> tableInfoList, String dbType) {

        this.tableInfoList = tableInfoList;

        this.dbType = dbType;
    }

    public void visit4HasWhere(Object target, SQLTableSource tableSource) {
        if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource exprTableSource = (SQLExprTableSource) tableSource;
            ShardingModifyBaseVisitor.TableInfo tableInfo = getTableInfoByName(exprTableSource.getName());
            if (tableInfo != null) {
                modify4HasWhere(target, tableInfo, exprTableSource);
            }
        } else if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource leftTableSource = joinTableSource.getLeft();
            SQLTableSource rightTableSource = joinTableSource.getRight();
            if (leftTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource leftExprTableSource = (SQLExprTableSource) leftTableSource;
                ShardingModifyBaseVisitor.TableInfo tableInfo = getTableInfoByName(leftExprTableSource.getName());
                if (tableInfo != null) {
                    modify4HasWhere(target, tableInfo, leftExprTableSource);
                }
            }
            if (rightTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource rightExprTableSource = (SQLExprTableSource) rightTableSource;
                ShardingModifyBaseVisitor.TableInfo tableInfo = getTableInfoByName(rightExprTableSource.getName());
                if (tableInfo != null) {
                    modify4HasWhere(target, tableInfo, rightExprTableSource);
                }
            }
            //两个以上的表连接
            if (leftTableSource instanceof SQLJoinTableSource) {
                visit4HasWhere(target, leftTableSource);
            }
            if (rightTableSource instanceof SQLJoinTableSource) {
                visit4HasWhere(target, rightTableSource);
            }
        }
    }

    private void modify4HasWhere(Object target, ShardingModifyBaseVisitor.TableInfo tableInfo, SQLExprTableSource exprTableSource) {
        exprTableSource.setExpr(tableInfo.getName() + tableInfo.getShardingName());
        hasModify = true;
    }

    public boolean visit4Insert(SQLInsertStatement x) {
        SQLExprTableSource tableSource = x.getTableSource();
        TableInfo tableInfo = getTableInfoByName(tableSource.getName());
        if (tableInfo == null) return true;
        tableSource.setExpr(tableInfo.getName() + tableInfo.getShardingName());
        hasModify = true;
        return true;
    }

    private ShardingModifyBaseVisitor.TableInfo getTableInfoByName(SQLName tableName) {
        return getTableInfoByName(tableName.getSimpleName());
    }

    private ShardingModifyBaseVisitor.TableInfo getTableInfoByName(String tableName) {
        return tableInfoList.stream().filter(c -> c.getName().equalsIgnoreCase(tableName)).findFirst().orElse(null);
    }

    public boolean hasModify() {
        return this.hasModify;
    }

    public String getDbType() {
        return dbType;
    }

    public static class TableInfo {
        private String name;
        private String shardingName;

        public TableInfo(String name, String shardingName) {
            this.name = name;
            this.shardingName = shardingName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShardingName() {
            return shardingName;
        }

        public void setShardingName(String shardingName) {
            this.shardingName = shardingName;
        }
    }
}
