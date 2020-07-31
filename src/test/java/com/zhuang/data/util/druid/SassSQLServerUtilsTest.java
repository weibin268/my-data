package com.zhuang.data.util.druid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SassSQLServerUtilsTest {

    @Test
    public void parseSql() {
        String sql = "update t1 set t1.name='11' where id=1123 ";
        List<SassModifyBaseVisitor.TableInfo> tableInfoList = new ArrayList<>();
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("t1", "id"));
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("t2", "id"));
        tableInfoList.add(new SassModifyBaseVisitor.TableInfo("sys_org", "id"));
        System.out.println(SassSQLServerUtils.parseSql(sql, tableInfoList, "company_id", () -> "inti"));
    }

}