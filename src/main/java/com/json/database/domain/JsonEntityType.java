package com.json.database.domain;

import java.io.Serializable;

/**
 * @author Alvin
 **/

public class JsonEntityType<ID extends Long> implements Serializable {

    private static long counter = 1;
    private long id;

    public JsonEntityType(ID id) {
        this.id = id;
    }

    public JsonEntityType() {
        this.id = counter;
        counter++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JsonEntityType{" +
                "id=" + id +
                '}';
    }
}
