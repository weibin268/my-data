package com.zhuang.data.util;

import com.zhuang.data.model.User;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class EntityUtilsTest {

    @Test
    public void convertToMap() {
        User user=new User();
        user.setName("zwb");
        Map map = EntityUtils.entityToMap(user,true);
        System.out.println(map);
    }
}