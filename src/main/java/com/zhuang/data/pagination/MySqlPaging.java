package com.zhuang.data.pagination;

import com.zhuang.data.util.SqlUtils;

public class MySqlPaging extends DbPaging {

    @Override
    public String getPageSql(String sql, String orderClause, int startRowIndex, int rowCount) {
        sql = SqlUtils.removeOrderByClause(sql);
        StringBuilder stringBuilder = new StringBuilder();
        if (orderClause != null && !orderClause.isEmpty()) {
            orderClause = "order by " + orderClause;
            stringBuilder.append(sql).append(" ").append(orderClause).append(" ").append("limit ")
                    .append(startRowIndex - 1).append(",").append(rowCount);
            return stringBuilder.toString();
        }
        throw new RuntimeException("Paged query must set orderBy Clause");
    }

}
