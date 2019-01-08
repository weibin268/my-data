package com.zhuang.data.pagination;

import com.zhuang.data.util.SqlUtils;

public abstract class DbPaging {
	
	public abstract String getPageSql(String sql, String orderClause, int startRowIndex, int rowCount);

    public String getCountSql(String strSql)
    {
        String tempSql = SqlUtils.removeOrderByClause(strSql);
        return " select count(1) from (\n" + tempSql + "\n) tt";
    }
}
