package com.zhuang.data.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String camelCaseToUnderscore(String text) {
        return camelCaseToUnderscore(text, false);
    }

    public static String camelCaseToUnderscore(String text, boolean isBigMode) {
        if (text == null || text.length() == 0) return text;
        Matcher matcher = Pattern.compile("(?<=[a-z])[A-Z]").matcher(text);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "_" + matcher.group().toLowerCase());
        }
        matcher.appendTail(stringBuffer);
        if (isBigMode) {
            return stringBuffer.toString().toUpperCase();
        } else {
            return stringBuffer.toString().toLowerCase();
        }
    }

    public static String underscoreToCamelCase(String text) {
        return underscoreToCamelCase(text, false);
    }

    public static String underscoreToCamelCase(String text, boolean isBigMode) {
        if (text == null || text.length() == 0) return text;
        if (isBigMode) {
            text = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        } else {
            text = text.toLowerCase();
        }
        Matcher matcher = Pattern.compile("\\_[A-Za-z]").matcher(text);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group().substring(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

}
