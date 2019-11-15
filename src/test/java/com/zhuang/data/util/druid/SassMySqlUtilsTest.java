package com.zhuang.data.util.druid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SassMySqlUtilsTest {

    @Test
    public void parseSql() {
        String sql = "select * from t1 as a inner join t2 as b on a.id=b.t1_id where b.name='zwb' or a.name='aaa'";
        List<String> tableNameList = new ArrayList<>();
        tableNameList.add("t1");
        tableNameList.add("t2");
        tableNameList.add("sys_org");
        System.out.println(SassMySqlUtils.parseSql(sql, tableNameList, "company_id", () -> "inti"));
    }

    @Test
    public void parseSql2() {
        String sql = "delete from t1 as a where id=1 delete t2 where id=1";
        List<String> tableNameList = new ArrayList<>();
        tableNameList.add("t1");
        tableNameList.add("t2");
        tableNameList.add("sys_org");
        System.out.println(SassMySqlUtils.parseSql(sql, tableNameList, "company_id", () -> "inti"));
    }

}