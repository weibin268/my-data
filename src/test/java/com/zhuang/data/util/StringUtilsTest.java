package com.zhuang.data.util;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void camelCaseToUnderscore() {
        System.out.println(StringUtils.camelCaseToUnderscore("getCreateByMe"));
        System.out.println(StringUtils.camelCaseToUnderscore("getCreateByMe", true));
    }

    @Test
    public void underscoreToCamelCase() {
        System.out.println(StringUtils.underscoreToCamelCase("Get_CreAte_By_ME"));
        System.out.println(StringUtils.underscoreToCamelCase("Get_CreAte_By_ME", true));
    }

}