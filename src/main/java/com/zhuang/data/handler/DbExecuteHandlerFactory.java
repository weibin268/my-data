package com.zhuang.data.handler;

import com.zhuang.data.config.MyDataProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhuang on 3/23/2018.
 */
public class DbExecuteHandlerFactory {

    private static List<DbExecutionHandler> dbExecutionHandlers;

    static {
        dbExecutionHandlers = new ArrayList<>();
        loadFromConfig();
    }

    public static List<DbExecutionHandler> getDbExecuteHandlers() {
        return dbExecutionHandlers;
    }

    public static void addDbExecutionHandler(DbExecutionHandler dbExecutionHandler) {
        dbExecutionHandlers.add(dbExecutionHandler);
    }

    private static void loadFromConfig() {
        MyDataProperties myDataProperties = MyDataProperties.getInstance();
        List<String> dbExecutionHandlers = myDataProperties.getDbExecutionHandlers();
        for (String item : dbExecutionHandlers) {
            try {
                Class clazz = Class.forName(item);
                boolean isDbExecutionHandlerClass = Arrays.stream(clazz.getInterfaces()).anyMatch(c -> c == DbExecutionHandler.class);
                if (isDbExecutionHandlerClass) {
                    DbExecuteHandlerFactory.addDbExecutionHandler((DbExecutionHandler) clazz.getConstructor().newInstance());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
