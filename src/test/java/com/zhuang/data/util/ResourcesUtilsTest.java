package com.zhuang.data.util;

import org.junit.Test;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Created by zhuang on 4/19/2018.
 */
public class ResourcesUtilsTest {

    @Test
    public void getResources() throws Exception {

        Pattern pattern = Pattern.compile(".*mapper.*xml$");

        Collection<String> list = ResourcesUtils.getResources(pattern);
        for(String name : list){
            System.out.println(name);
        }

    }
}