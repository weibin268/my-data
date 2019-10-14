package com.zhuang.data.orm.sql;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.model.User;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;
import org.junit.Test;

/**
 * Created by zhuang on 12/30/2017.
 */
public class BaseSqlBuilderTest {

    SqlBuilder sqlBuilder =SqlBuilderFactory.createSqlBuilder(DbDialect.MySQL,new TableMapping(User.class), PlaceHolderType.NumberSign);

    @Test
    public void buildSelect() throws Exception {
        System.out.println(sqlBuilder.buildSelect().getSql());
    }

    @Test
    public void buildSelectCount() throws Exception {
        System.out.println(sqlBuilder.buildSelectCount().getSql());
    }

    @Test
    public void buildInsert() throws Exception {
        System.out.println(sqlBuilder.buildInsert().getSql());
    }

    @Test
    public void buildUpdate() throws Exception {
        System.out.println(sqlBuilder.buildUpdate().getSql());
    }

    @Test
    public void buildDelete() throws Exception {
        System.out.println(sqlBuilder.buildDelete().getSql());
    }

}