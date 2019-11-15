package com.zhuang.data.util.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.function.Supplier;

public class SassMySqlUtils {

    public static String parseSql(String sql, List<String> tableNameList, String fieldName, Supplier<String> valueSupplier) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, JdbcConstants.MYSQL);
        List<SQLStatement> sqlStatementList = sqlStatementParser.parseStatementList();
        SassModifyMySqlVisitor sassModifyMySqlVisitor = new SassModifyMySqlVisitor(tableNameList, fieldName, valueSupplier);
        sqlStatementList.forEach(c -> c.accept(sassModifyMySqlVisitor));
        if (sassModifyMySqlVisitor.hasModify()) {
            StringBuilder sbSql = new StringBuilder();
            sqlStatementList.forEach(c -> sbSql.append('\n').append(c.toString()));
            return sbSql.toString();
        } else {
            return null;
        }
    }
}
