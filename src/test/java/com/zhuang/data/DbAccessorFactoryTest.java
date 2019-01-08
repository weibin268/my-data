package com.zhuang.data;

import com.zhuang.data.config.JdbcProperties;
import com.zhuang.data.model.User;
import org.junit.Test;

/**
 * Created by zhuang on 3/7/2018.
 */
public class DbAccessorFactoryTest {

    @Test
    public void createMyBatisDbAccessor() throws Exception {

        /*
        jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/zwb?useSSL=true&useUnicode=true&characterEncoding=UTF-8
jdbc.username=root
jdbc.password=2681645

        */

        JdbcProperties jdbcProperties=new JdbcProperties("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/zwb?useSSL=true&useUnicode=true&characterEncoding=UTF-8",
                "root","2681645");

        DbAccessor dbAccessor = DbAccessorFactory.createMyBatisDbAccessor(jdbcProperties.getProperties(),true);


        User user = dbAccessor.queryEntity("mapper.User.selectById", "0141f480-2f3d-45d9-98cb-e5051f5d8c02", User.class);

        System.out.println(user);

    }

}