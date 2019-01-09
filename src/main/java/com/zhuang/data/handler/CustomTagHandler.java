package com.zhuang.data.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuang
 **/
public abstract class CustomTagHandler {

    //匹配格式：@env{user.userId}
    private Pattern pattern;

    public CustomTagHandler(String tagName) {
        pattern = Pattern.compile("(?<g1>@" + tagName + "\\{)(?<g2>.+?)(?<g3>\\})");
    }

    public String handle(String sql) {
        Matcher matcher = pattern.matcher(sql);
        StringBuffer sbResult = new StringBuffer();
        while (matcher.find()) {
            String g1 = matcher.group("g1");
            String content = matcher.group("g2");
            String g3 = matcher.group("g3");
            matcher.appendReplacement(sbResult, handleInternal(content));
        }
        if (sbResult.length() > 0) {
            matcher.appendTail(sbResult);
            return sbResult.toString();
        } else {
            return sql;
        }
    }

    protected abstract String handleInternal(String tagContent);
}
