package com.zhuang.data.util.druid;

import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerSelectQueryBlock;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerInsertStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerUpdateStatement;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.function.Supplier;

public class ShardingModifySQLServerVisitor extends SQLServerASTVisitorAdapter {

    private ShardingModifyBaseVisitor baseVisitor;

    public ShardingModifyBaseVisitor getBaseVisitor() {
        return baseVisitor;
    }

    public ShardingModifySQLServerVisitor(List<ShardingModifyBaseVisitor.TableInfo> tableInfoList ) {
        baseVisitor = new ShardingModifyBaseVisitor(tableInfoList,   JdbcConstants.SQL_SERVER);
    }

    @Override
    public boolean visit(SQLServerSelectQueryBlock x) {
        baseVisitor.visit4HasWhere(x, x.getFrom());
        return true;
    }

    @Override
    public boolean visit(SQLServerUpdateStatement x) {
        baseVisitor.visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        baseVisitor.visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(SQLServerInsertStatement x) {
        return baseVisitor.visit4Insert(x);
    }


}
