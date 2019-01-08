package com.zhuang.data.jdbc.util;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.mybatis.util.JdbcTypeUtils;
import org.junit.Test;

public class JdbcTypeUtilsTest {

    @Test
    public void getJdbcTypeName() {

        System.out.println(JdbcTypeUtils.getJdbcTypeName(DbDialect.Oracle,"".getClass()));

    }
}