package com.zhuang.data.pagination;

import com.zhuang.data.util.SqlUtils;

public class MSSQLPaging extends DbPaging {

    @Override
    public String getPageSql(String sql, String orderClause, int startRowIndex, int rowCount) {
        sql = SqlUtils.removeOrderByClause(sql);
        StringBuilder stringBuilder = new StringBuilder();
        if (orderClause != null && !orderClause.isEmpty()) {
            orderClause = "order by " + orderClause;
            stringBuilder.append("select * from (select row_number() over(").append(orderClause).append(") as rownum,* from (\n");
            stringBuilder.append(sql);
            stringBuilder.append("\n ) as _table1) as _table2 where (rownum <=").append(startRowIndex + rowCount - 1).append(" and rownum>= ").append(startRowIndex).append(" ) ");

            return stringBuilder.toString();
        }
        throw new RuntimeException("Paged query must set orderBy Clause");

    }

}
