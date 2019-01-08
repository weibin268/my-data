package com.zhuang.data.orm.mapping;

import com.zhuang.data.model.User;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhuang on 12/30/2017.
 */
public class TableMappingTest {

    TableMapping tableMapping=new TableMapping(User.class);

    @Test
    public void getTableName() throws Exception {
        System.out.println(tableMapping.getTableName());
    }

    @Test
    public void getColumns() throws Exception {
        List<ColumnMapping> columnMappings = tableMapping.getColumns();
        for (ColumnMapping item :
                columnMappings) {
            System.out.println(item);
        }
    }

    @Test
    public void getKeyColumns() throws Exception {
        List<ColumnMapping> columnMappings = tableMapping.getKeyColumns();
        for (ColumnMapping item :
                columnMappings) {
            System.out.println(item);
        }
    }

    @Test
    public void getInsertColumns() throws Exception {
        List<ColumnMapping> columnMappings = tableMapping.getInsertColumns();
        for (ColumnMapping item :
                columnMappings) {
            System.out.println(item);
        }
    }

    @Test
    public void getUpdateColumns() throws Exception {
        List<ColumnMapping> columnMappings = tableMapping.getUpdateColumns();
        for (ColumnMapping item :
                columnMappings) {
            System.out.println(item);
        }
    }

}