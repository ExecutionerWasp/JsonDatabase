package com.json.database.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Alvin
 **/

public class JsonEntityType<ID extends Long> implements Serializable {

    private long id = UUID.randomUUID().getMostSignificantBits();

    public JsonEntityType(ID id) {
        this.id = id;
    }

    public JsonEntityType() {
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
