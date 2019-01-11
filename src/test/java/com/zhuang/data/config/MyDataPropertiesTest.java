package com.zhuang.data.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyDataPropertiesTest {

    @Test
    public void getDbExecutionHandlers() {
        MyDataProperties myDataProperties=new MyDataProperties();
        myDataProperties.getDbExecutionHandlers().forEach(System.out::println);
    }
}