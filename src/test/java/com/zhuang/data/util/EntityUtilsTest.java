package com.zhuang.data.util;

import com.zhuang.data.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class EntityUtilsTest {

    public static class Person {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }

    public static class Lily extends Person {
        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        private Integer age;

        @Override
        public String toString() {
            return "Lily{" +
                    "name='" + getName() + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    public void mapToEntity() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zwb");
        map.put("age", 10);
        Lily lily = EntityUtils.mapToEntity(map, Lily.class);
        Assert.assertEquals("zwb", lily.getName());
        System.out.println(lily);
    }

    @Test
    public void mapToEntity2() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zwb");
        map.put("age", 10);
        Lily lily = EntityUtils.mapToEntity2(map, Lily.class);
        Assert.assertEquals(null, lily.getName());
        System.out.println(lily);
    }

    @Test
    public void convertToMap() {
        User user = new User();
        user.setName("zwb");
        Map map = EntityUtils.entityToMap(user, true);
        System.out.println(map);
    }


}