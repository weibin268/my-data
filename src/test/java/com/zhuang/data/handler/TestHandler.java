package com.zhuang.data.handler;

/**
 * Created by zhuang on 3/23/2018.
 */
public class TestHandler implements DbExecutionHandler {
    @Override
    public void handle(DbExecutionContext context) {

        System.out.println(context.getSql());

        context.setSql(context.getSql()+" and 1=2");

        System.out.println(context.getSql());


    }
}
