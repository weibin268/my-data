package com.zhuang.data.orm.sql;

import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;

/**
 * @author zhuang
 * @create 6/30/18 10:50 AM
 **/
public class MSSQLSqlBuilder extends BaseSqlBuilder{
    public MSSQLSqlBuilder(TableMapping tableMapping, PlaceHolderType placeHolderType) {
        super(tableMapping, placeHolderType);
    }
}
