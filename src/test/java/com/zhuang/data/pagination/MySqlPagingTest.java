package com.zhuang.data.pagination;

import org.junit.Test;

import static org.junit.Assert.*;

public class MySqlPagingTest {

    @Test
    public void getPageSql() {

        MySqlPaging mySqlPaging = new MySqlPaging();

        String a="";

        System.out.println(mySqlPaging.getPageSql(
                "select * from tes_user order by id",a+"a",
                1,2));

    }
}