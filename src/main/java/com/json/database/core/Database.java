package com.json.database.core;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

public enum Database implements DatabaseExecutor {

    INSTANCE;


    @Override
    public DatabaseConfiguration<Employee, Long> config() {
        return new DatabaseConfiguration<>() {
            @Override
            public Supplier<Employee> instance() {
                return Employee::new;
            }

            @Override
            public String document() {
                return "document";
            }

            @Override
            public Long id() {
                return UUID.randomUUID().getLeastSignificantBits();
            }

            @Override
            public String url() {
                return "";
            }
        };
    }

    @Override
    public void clean() {

    }

    @Override
    public File export() {
        return null;
    }

    @Override
    public void bring(File db) {

    }
}
