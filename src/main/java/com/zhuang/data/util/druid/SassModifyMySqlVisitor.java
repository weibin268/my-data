package com.zhuang.data.util.druid;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.function.Supplier;

public class SassModifyMySqlVisitor extends MySqlASTVisitorAdapter {

    private boolean hasModify = false;
    private String dbType = JdbcConstants.MYSQL;

    private List<TableInfo> tableInfoList;
    private String fieldName;
    Supplier<String> valueSupplier;


    public SassModifyMySqlVisitor(List<TableInfo> tableInfoList, String fieldName, Supplier<String> valueSupplier) {
        this.tableInfoList = tableInfoList;
        this.fieldName = fieldName;
        this.valueSupplier = valueSupplier;
    }

    @Override
    public boolean visit(MySqlSelectQueryBlock x) {
        visit4HasWhere(x, x.getFrom());
        return true;
    }

    @Override
    public boolean visit(MySqlUpdateStatement x) {
        visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(MySqlDeleteStatement x) {
        visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(MySqlInsertStatement x) {
        SQLExprTableSource tableSource = x.getTableSource();
        if (!checkTableName(tableSource.getName())) return true;
        List<SQLExpr> exprColumnList = x.getColumns();
        List<SQLInsertStatement.ValuesClause> valuesClauseList = x.getValuesList();
        if (exprColumnList.stream().anyMatch(c -> ((SQLIdentifierExpr) c).getName().equalsIgnoreCase(fieldName))) {
            return true;
        }
        SQLIdentifierExpr fieldExpr = new SQLIdentifierExpr(fieldName);
        exprColumnList.add(fieldExpr);
        valuesClauseList.forEach(item -> {
            SQLCharExpr valueExpr = new SQLCharExpr(valueSupplier.get());
            item.getValues().add(valueExpr);
        });
        hasModify = true;
        return true;
    }

    private void visit4HasWhere(Object target, SQLTableSource tableSource) {
        if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource exprTableSource = (SQLExprTableSource) tableSource;
            if (checkTableName(exprTableSource.getName())) {
                modify4HasWhere(target, exprTableSource.getAlias());
            }
        } else if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource leftTableSource = joinTableSource.getLeft();
            SQLTableSource rightTableSource = joinTableSource.getRight();
            if (leftTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource leftExprTableSource = (SQLExprTableSource) leftTableSource;
                if (checkTableName(leftExprTableSource.getName())) {
                    String alias = leftExprTableSource.getAlias() == null ? leftExprTableSource.getName().getSimpleName() : leftExprTableSource.getAlias();
                    modify4HasWhere(target, alias);
                }
            }
            if (rightTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource rightExprTableSource = (SQLExprTableSource) rightTableSource;
                if (checkTableName(rightExprTableSource.getName())) {
                    String alias = rightExprTableSource.getAlias() == null ? rightExprTableSource.getName().getSimpleName() : rightExprTableSource.getAlias();
                    modify4HasWhere(target, alias);
                }
            }
        }
    }

    private void modify4HasWhere(Object target, String alias) {
        if (target instanceof MySqlSelectQueryBlock) {
            MySqlSelectQueryBlock statement = (MySqlSelectQueryBlock) target;
            statement.setWhere(appendWhereExpr(statement.getWhere(), getAppendWhere(alias)));
        } else if (target instanceof MySqlUpdateStatement) {
            MySqlUpdateStatement statement = (MySqlUpdateStatement) target;
            statement.setWhere(appendWhereExpr(statement.getWhere(), getAppendWhere(alias)));
        } else if (target instanceof MySqlDeleteStatement) {
            MySqlDeleteStatement statement = (MySqlDeleteStatement) target;
            statement.setWhere(appendWhereExpr(statement.getWhere(), getAppendWhere(alias)));
        }
    }

    private boolean checkTableName(SQLName sqlName) {
        return checkTableName(sqlName.getSimpleName());
    }

    private boolean checkTableName(String tableName) {
        return tableInfoList.stream().anyMatch(c -> c.getName().equalsIgnoreCase(tableName));
    }

    private SQLExpr appendWhereExpr(SQLExpr whereExpr, String appendWhere) {
        SQLExpr result;
        SQLExprParser appendWhereParser = SQLParserUtils.createExprParser(appendWhere, dbType);
        SQLExpr appendWhereExpr = appendWhereParser.expr();
        if (whereExpr == null) {
            result = appendWhereExpr;
        } else {
            if (!existsWhereExpr(whereExpr, ((SQLBinaryOpExpr) appendWhereExpr).getLeft())) {
                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(whereExpr, SQLBinaryOperator.BooleanAnd, appendWhereExpr);
                result = newWhereExpr;
            } else {
                result = whereExpr;
            }
        }
        if (result != whereExpr) {
            hasModify = true;
        }
        return result;
    }

    private boolean existsWhereExpr(SQLExpr whereExpr, SQLExpr appendFieldExpr) {
        if (!(whereExpr instanceof SQLBinaryOpExpr)) return false;
        SQLBinaryOpExpr whereBinaryOpExpr = (SQLBinaryOpExpr) whereExpr;
        if (sameFieldExpr(whereBinaryOpExpr.getLeft(), appendFieldExpr)) {
            return true;
        }
        if (sameFieldExpr(whereBinaryOpExpr.getRight(), appendFieldExpr)) {
            return true;
        }
        return false;
    }

    public boolean sameFieldExpr(SQLExpr fieldExpr, SQLExpr appendFieldExpr) {
        if (fieldExpr.getClass().getName().equals(appendFieldExpr.getClass().getName())) {
            if (appendFieldExpr instanceof SQLPropertyExpr) {
                SQLPropertyExpr appendFieldPropertyExpr = (SQLPropertyExpr) appendFieldExpr;
                SQLPropertyExpr fieldPropertyExpr = (SQLPropertyExpr) fieldExpr;
                if (appendFieldPropertyExpr.getName().equalsIgnoreCase(fieldPropertyExpr.getName()) && appendFieldPropertyExpr.getOwnernName().equalsIgnoreCase(fieldPropertyExpr.getOwnernName())) {
                    return true;
                }
            } else if (appendFieldExpr instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr appendFieldIdentifierExpr = (SQLIdentifierExpr) appendFieldExpr;
                SQLIdentifierExpr fieldIdentifierExpr = (SQLIdentifierExpr) fieldExpr;
                if (appendFieldIdentifierExpr.getName().equalsIgnoreCase(fieldIdentifierExpr.getName())) {
                    return true;
                }
            }
        } else if (fieldExpr instanceof SQLBinaryOpExpr) {
            return existsWhereExpr(fieldExpr, appendFieldExpr);
        }
        return false;
    }

    private String getAppendWhere() {
        return getAppendWhere(null);
    }

    private String getAppendWhere(String alias) {
        String result = fieldName + " = '" + valueSupplier.get() + "'";
        if (alias != null) {
            result = alias + "." + result;
        }
        return result;
    }

    public boolean hasModify() {
        return this.hasModify;
    }

    public static class TableInfo {
        private String name;
        private String primaryKey;

        public TableInfo(String name, String primaryKey) {
            this.name = name;
            this.primaryKey = primaryKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(String primaryKey) {
            this.primaryKey = primaryKey;
        }
    }
}
