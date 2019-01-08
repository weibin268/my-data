package com.zhuang.data.jdbc;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.DbAccessorFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuang on 12/30/2017.
 */
public class JdbcDbAccessorTest {
    @Test
    public void queryEntity() throws Exception {

        DbAccessor dbAccessor = DbAccessorFactory.getJdbcDbAccessor();
        System.out.println(dbAccessor.getDbDialect());

    }

}