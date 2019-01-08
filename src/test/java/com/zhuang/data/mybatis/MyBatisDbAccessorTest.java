package com.zhuang.data.mybatis;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.model.PageInfo;
import com.zhuang.data.model.User;
import org.junit.Test;

import java.util.*;

/**
 * Created by zhuang on 12/30/2017.
 */
public class MyBatisDbAccessorTest {

    @Test
    public void dropTable() {
        DbAccessor.get().executeNonQuery("mapper.User.dropTable", null);
    }

    @Test
    public void createTable() {
        DbAccessor.get().executeNonQuery("mapper.User.createTable", null);
    }

    @Test
    public void insert() {
        DbAccessor dbAccessor = DbAccessor.get();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName("张三" + i);
            user.setLoginId("zs" + i);
            user.setPassword("123");
            user.setCreatedBy("admin");
            user.setCreatedTime(new Date());
            user.setSex("M");
            user.setStatus(1);
            System.out.println(dbAccessor.insert(user));
        }
    }

    @Test
    public void update() {

        DbAccessor dbAccessor = DbAccessor.get();

        String id = UUID.randomUUID().toString();

        System.out.println("id:" + id);

        User user1 = new User();
        user1.setId(id);
        user1.setName("张三");
        user1.setLoginId("zs");
        user1.setPassword("123");
        user1.setCreatedBy("admin");
        user1.setCreatedTime(new Date());
        user1.setSex("M");
        user1.setStatus(1);

        dbAccessor.insert(user1);

        User user2 = dbAccessor.select(id, User.class);

        System.out.println("before update:" + user2);

        user2.setName("李四");
        int updateResult = dbAccessor.update(user2);

        System.out.println("update result:" + updateResult);

        User user3 = dbAccessor.select(id, User.class);

        System.out.println("after update:" + user3);

    }

    @Test
    public void update2() {
        DbAccessor dbAccessor = DbAccessor.get();
        String id = UUID.randomUUID().toString();
        System.out.println("id:" + id);
        User user1 = new User();
        user1.setId(id);
        user1.setName("张三");
        user1.setLoginId("zs");
        user1.setPassword("123");
        user1.setCreatedBy("admin");
        user1.setCreatedTime(new Date());
        user1.setSex("M");
        user1.setStatus(1);
        dbAccessor.insert(user1);
        User user2 = dbAccessor.select(id, User.class);
        System.out.println("before update:" + user2);
        Map<String, Object> param = new HashMap<>();
        param.put("id", user2.getId());
        param.put("name", "zwb");
        param.put("orgId", "001");
        int updateResult = dbAccessor.update(user2, new String[]{"id", "name", "orgId"});
        System.out.println("update result:" + updateResult);
        User user3 = dbAccessor.select(id, User.class);
        System.out.println("after update:" + user3);
    }

    @Test
    public void select() {
        DbAccessor dbAccessor = DbAccessor.get();
        String id = UUID.randomUUID().toString();
        System.out.println("id:" + id);
        User user1 = new User();
        user1.setId(id);
        user1.setName("张三");
        user1.setLoginId("zs");
        user1.setPassword("123");
        user1.setCreatedBy("admin");
        user1.setCreatedTime(new Date());
        user1.setSex("M");
        user1.setStatus(1);
        dbAccessor.insert(user1);
        User user2 = dbAccessor.select(id, User.class);
        System.out.println(user2);
    }

    @Test
    public void insertOrUpdate() {
        DbAccessor dbAccessor = DbAccessor.get();
        User user = new User();
        user.setId("2");
        user.setName("张三2");
        user.setLoginId("zs");
        user.setPassword("123");
        user.setCreatedBy("admin");
        user.setCreatedTime(new Date());
        user.setSex("M");
        user.setStatus(1);
        System.out.println(dbAccessor.insertOrUpdate(user));
    }

    @Test
    public void selectByMap() {
        DbAccessor dbAccessor = DbAccessor.get();
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", "张三");
        dbAccessor.selectByMap(parameter, User.class).stream().forEach(System.out::println);
    }

    @Test
    public void queryEntity() {
        DbAccessor dbAccessor = DbAccessor.get();
        String id = UUID.randomUUID().toString();
        System.out.println("id:" + id);
        User user1 = new User();
        user1.setId(id);
        user1.setName("张三");
        user1.setLoginId("zs");
        user1.setPassword("123");
        user1.setCreatedBy("admin");
        user1.setCreatedTime(new Date());
        user1.setSex("M");
        user1.setStatus(1);
        dbAccessor.insert(user1);
        User user2 = dbAccessor.queryEntity("mapper.User.selectById", id, User.class);
        System.out.println(user2);
    }

    @Test
    public void pageQueryEntity() {
        DbAccessor dbAccessor = DbAccessor.get();
        String orderClause = "Id";
        int pageIndex = 1;
        int rowCount = 10;
        Map<String, Object> param = new HashMap<String, Object>();
        //param.put("value", "1");
        PageInfo pageInfo = new PageInfo(pageIndex, rowCount, orderClause);
        List<User> users = dbAccessor.pageQueryEntities(
                "mapper.User.selectPage", pageInfo, param, User.class);
        System.out.println("total row count:" + pageInfo.getTotalRowCount());
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void queryMap() {
        DbAccessor dbAccessor = DbAccessor.get();
        String id = UUID.randomUUID().toString();
        System.out.println("id:" + id);
        User user1 = new User();
        user1.setId(id);
        user1.setName("张三");
        user1.setLoginId("zs");
        user1.setPassword("123");
        user1.setCreatedBy("admin");
        user1.setCreatedTime(new Date());
        user1.setSex("M");
        user1.setStatus(1);
        dbAccessor.insert(user1);
        System.out.println("query map:");
        Map<String, Object> map = dbAccessor.queryEntity("mapper.User.selectMap", id, Map.class);
        System.out.println(map);
        System.out.println("query maps:");
        List<Map> maps = dbAccessor.queryEntities("mapper.User.selectMaps", null, Map.class);
        for (Map<String, Object> item : maps) {
            System.out.println(item);
        }
    }

    @Test
    public void queryCount() {
        DbAccessor dbAccessor = DbAccessor.get();
        Integer integer = dbAccessor.queryEntity("mapper.User.selectCount", null,
                Integer.class);
        System.out.println(integer);
    }

    @Test
    public void delete() {
        DbAccessor dbAccessor = DbAccessor.get();
        String id = UUID.randomUUID().toString();
        System.out.println("id:" + id);
        User user1 = new User();
        user1.setId(id);
        user1.setName("张三");
        user1.setLoginId("zs");
        user1.setPassword("123");
        user1.setCreatedBy("admin");
        user1.setCreatedTime(new Date());
        user1.setSex("M");
        user1.setStatus(1);
        dbAccessor.insert(user1);
        User user2 = dbAccessor.select(id, User.class);
        System.out.println(user2);
        dbAccessor.delete(id, User.class);
        user2 = dbAccessor.select(id, User.class);
        System.out.println(user2);
    }

    @Test
    public void commit() {
        DbAccessor dbAccessor = DbAccessor.create();
        try {
            for (int i = 0; i < 100; i++) {
                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setName("张三" + i);
                user.setLoginId("zs" + i);
                user.setPassword("123");
                user.setCreatedBy("admin");
                user.setCreatedTime(new Date());
                user.setSex("M");
                user.setStatus(1);
                if (i > 50) {
                    throw new Exception("aa");
                }
                System.out.println(dbAccessor.executeNonQuery("mapper.User.insert", user));
                //System.out.println(dbAccessor.insert(user));
            }
            dbAccessor.commit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            dbAccessor.rollback();
        } finally {

            dbAccessor.close();
        }
    }

}