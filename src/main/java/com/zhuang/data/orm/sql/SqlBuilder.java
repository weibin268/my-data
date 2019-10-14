package com.zhuang.data.orm.sql;

public interface SqlBuilder {

    BuildResult buildSelect();

    BuildResult buildSelectCount();

    BuildResult buildInsert();

    BuildResult buildUpdate();

    BuildResult buildDelete();

}
