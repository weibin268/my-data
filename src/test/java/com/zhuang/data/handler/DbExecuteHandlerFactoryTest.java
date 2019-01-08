package com.zhuang.data.handler;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.model.User;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhuang on 3/23/2018.
 */
public class DbExecuteHandlerFactoryTest {

    @Test
    public void addDbExecutionHandler() throws Exception {

        DbExecuteHandlerFactory.addDbExecutionHandler(new TestHandler());

        DbAccessor dbAccessor = DbAccessor.get();

        String id= UUID.randomUUID().toString();

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

}