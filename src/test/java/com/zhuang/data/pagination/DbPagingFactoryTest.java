package com.zhuang.data.pagination;

import com.zhuang.data.enums.DbDialect;
import org.junit.Test;

/**
 * Created by zhuang on 12/30/2017.
 */
public class DbPagingFactoryTest {
    @Test
    public void getDbPaging() throws Exception {

        DbPaging dbPaging = DbPagingFactory.GetDbPaging(DbDialect.MySQL);

        String strSql="select * from sys_user order by id ";

        System.out.println(dbPaging.getCountSql(strSql));

        System.out.println(dbPaging.getPageSql(strSql, "id", 1, 10));

    }

}