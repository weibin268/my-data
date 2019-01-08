package com.zhuang.data.orm.mapping;

import com.zhuang.data.jdbc.JdbcUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EntityMappingTest {

    @Test
    public void test() {
        EntityMapping entityMapping = new EntityMapping("sys_|user");
        System.out.println(entityMapping);
    }

    @Test
    public void retrieveEntityMappingList() {
        List<EntityMapping> entityMappings = EntityMapping.retrieveEntityMappingList(JdbcUtils.getConnection());
        for (EntityMapping entityMapping :
                entityMappings) {
            System.out.println(entityMapping);
        }
    }

}