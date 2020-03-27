package com.json.database.core;

import com.json.database.core.test.Employee;
import com.json.database.core.util.Log;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

public enum Database implements DatabaseExecutor {

    INSTANCE;


    @Override
    public DatabaseConfiguration<Employee, Long> config() {
        Log.info("Configuring database...");
        return new DatabaseConfiguration<>() {
            @Override
            public Supplier<Employee> instance() {
                return Employee::new;
            }

            @Override
            public String document() {
                return instance().get().getClass().getSimpleName().toLowerCase();
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
}
