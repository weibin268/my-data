package com.zhuang.data.orm.sql;

import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;

/**
 * @author zhuang
 **/
public class MySQLSqlBuilder extends BaseSqlBuilder{
    public MySQLSqlBuilder(TableMapping tableMapping, PlaceHolderType placeHolderType) {
        super(tableMapping, placeHolderType);
    }
}
