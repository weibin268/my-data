package com.zhuang.data.jdbc;

import com.zhuang.data.model.User;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JdbcUtilsTest {

    @Test
    public void executeNonQuery() {
        String sql = "insert into tes_user(id,login_id,name,password,sex,status,created_time,modified_time,created_by,modified_by,org_id)\n" +
                "values(uuid(),?,?,'123','F',1,now(),now(),'zwb','zwb',uuid());";
        JdbcUtils.executeNonQuery(sql, "zhuang", "zhuang2");
    }

    @Test
    public void queryEntities() {
        List<User> userList = JdbcUtils.queryEntities( User.class,"select * from tes_user");
        for (User item : userList) {
            System.out.println(item);
        }
    }

    @Test
    public void queryEntity() {
        User user = JdbcUtils.queryEntity(User.class,"select * from tes_user limit 1");
        System.out.println(user);
    }

    @Test
    public void queryMap() {
        Map user = JdbcUtils.queryMap("select * from tes_user limit 1");
        System.out.println(user);
    }

    @Test
    public void queryMaps() {
        List<Map> userList = JdbcUtils.queryMaps("select * from tes_user");
        for (Map item : userList) {
            System.out.println(item);
        }
    }

}