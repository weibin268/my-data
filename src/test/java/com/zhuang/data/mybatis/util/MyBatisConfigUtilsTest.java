package com.zhuang.data.mybatis.util;

import org.junit.Test;

/**
 * Created by zhuang on 4/18/2018.
 */
public class MyBatisConfigUtilsTest {

    @Test
    public void resolveMapperLocations() throws Exception {

        MyBatisConfigUtils.resolveMapperLocations("config/mybatis-config.xml");

    }

}