package com.zhuang.data.pagination;

import com.zhuang.data.enums.DbDialect;

public class DbPagingFactory {
	
	public static DbPaging GetDbPaging(DbDialect dbDialect) {
		DbPaging dbPaging = null;
		if (dbDialect == DbDialect.MySQL) {
			dbPaging = new MySqlPaging();
		} else if (dbDialect == DbDialect.MSSQL) {
			dbPaging = new MSSQLPaging();
		} else if (dbDialect == DbDialect.Oracle) {
			dbPaging = new OraclePaging();
		}
		return dbPaging;
	}
}
