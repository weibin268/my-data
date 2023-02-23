package com.zhuang.data.util.druid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ShardingSQLServerUtilsTest {

    @Test
    public void parseSql() {
        String sql = "select t1.* from sys_org t1 inner join sys_org2 t2 on t1.abc=t2.abc where t1.aid=1123 ";
        List<ShardingModifyBaseVisitor.TableInfo> tableInfoList = new ArrayList<>();
        tableInfoList.add(new ShardingModifyBaseVisitor.TableInfo("sys_org", "200202"));
        tableInfoList.add(new ShardingModifyBaseVisitor.TableInfo("sys_org2", "111111"));
        System.out.println(ShardingSQLServerUtils.parseSql(sql, tableInfoList));
    }

}
