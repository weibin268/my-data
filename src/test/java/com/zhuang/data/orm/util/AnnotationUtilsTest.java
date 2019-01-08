package com.zhuang.data.orm.util;

import com.zhuang.data.model.User;
import org.junit.Test;

public class AnnotationUtilsTest {

    @Test
    public void getTableName() {
        System.out.println(AnnotationUtils.getTableName(User.class));
    }

    @Test
    public void getColumnName() throws NoSuchFieldException {
        System.out.println(AnnotationUtils.getColumnName(User.class.getDeclaredField("loginId")));
    }

}
