package com.zhuang.data.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SqlUtilsTest {

    @Test
    public void removeOrderByClause() {
    }

    @Test
    public void removeSqlKeyWord() {

        System.out.println(SqlUtils.removeSqlKeyWord("SELECT * from sys_user"));

    }
}