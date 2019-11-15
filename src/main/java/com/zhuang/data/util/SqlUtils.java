package com.zhuang.data.util;

import java.util.regex.Pattern;

public class SqlUtils {

    private static Pattern orderByClausePattern = Pattern.compile("[{]\\s*[?]\\s*order\\s+by[\\d|\\w|\\s|$|#|@|:|-|_]*[}]|order\\s+by[\\d|\\w|\\s|$|#|@|:|-|_]*", Pattern.CASE_INSENSITIVE);
    private static Pattern sqlKeyWordPattern = Pattern.compile("select|insert|delete|from", Pattern.CASE_INSENSITIVE);

    public static String removeOrderByClause(String sql) {
        String result;
        result = orderByClausePattern.matcher(sql).replaceAll("");
        return result;
    }

    public static String removeSqlKeyWord(String text) {
        return sqlKeyWordPattern.matcher(text).replaceAll("");
    }
}
