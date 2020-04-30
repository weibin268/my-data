package com.zhuang.data.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataTypeUtilsTest {

    @Test
    public void getJavaTypeFromDbType() {

        System.out.println(DataTypeUtils.getJavaTypeFromDbType("date").getName());

    }
}