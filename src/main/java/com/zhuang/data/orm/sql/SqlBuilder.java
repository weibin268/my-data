package com.zhuang.data.orm.sql;

public interface SqlBuilder {

    BuildResult buildSelect();

    BuildResult buildInsert();

    BuildResult buildUpdate();

    BuildResult buildDelete();

}
