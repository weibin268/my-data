package com.zhuang.data.pagination;

import com.zhuang.data.util.SqlUtils;

public class OraclePaging extends DbPaging {

    @Override
    public String getPageSql(String sql, String orderClause, int startRowIndex, int rowCount) {
        sql = SqlUtils.removeOrderByClause(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from (select * from (select row_.*, rownum rownum_ from ( ");
        stringBuilder.append(sql);
        if (orderClause != null && !orderClause.isEmpty()) {
            stringBuilder.append(" Order By ").append(orderClause);
        }
        stringBuilder.append("\n ) row_) where rownum_ <= ").append(startRowIndex + rowCount - 1).append(") where rownum_ >= ").append(startRowIndex);
        return stringBuilder.toString();
    }
}
