package com.zhuang.data.util.druid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SassMySqlUtilsTest {

    @Test
    public void parseSql() {
        String sql = "select * from t1 as a inner join t2 as b on a.id=b.t1_id where a.id='1234'";
        List<SassModifyBaseVisitor.TableInfo> tableInfoList = new ArrayList<>();
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("t1","id"));
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("t2","id"));
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("sys_org","id"));
        System.out.println(SassMySqlUtils.parseSql(sql, tableInfoList, "company_id", () -> "inti"));
    }

    @Test
    public void parseSql2() {
        String sql = "delete from t1 as a where id=1 delete t2 where id=1";
        List<SassModifyBaseVisitor.TableInfo> tableInfoList = new ArrayList<>();
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("t1","id"));
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("t2","id"));
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("sys_org","id"));
        System.out.println(SassMySqlUtils.parseSql(sql, tableInfoList, "company_id", () -> "inti"));
    }

}