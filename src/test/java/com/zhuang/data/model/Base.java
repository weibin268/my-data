package com.zhuang.data.model;

import com.zhuang.data.orm.annotation.Id;
import com.zhuang.data.orm.annotation.UnderscoreNaming;

@UnderscoreNaming
public class Base {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
