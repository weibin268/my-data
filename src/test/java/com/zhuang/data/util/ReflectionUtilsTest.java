package com.zhuang.data.util;

import com.zhuang.data.model.User;
import com.zhuang.data.orm.annotation.UnderscoreNaming;
import org.junit.Test;

import java.util.Arrays;

public class ReflectionUtilsTest {

    @Test
    public void getDeclaredFields() {
        Arrays.stream(ReflectionUtils.getDeclaredFields(User.class)).forEach(System.out::println);
    }

    @Test
    public void getAnnotation() {
        UnderscoreNaming underscoreNaming = ReflectionUtils.getAnnotation(User.class, UnderscoreNaming.class);
        System.out.println(underscoreNaming);
    }
}