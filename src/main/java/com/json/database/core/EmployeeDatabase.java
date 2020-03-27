package com.json.database.core;

import com.json.database.core.test.Employee;

import java.util.function.Supplier;

/**
 * @author Alvin
 **/

public enum EmployeeDatabase implements DatabaseRepository<Employee, Long> {
    INSTANCE;

    @Override
    public Supplier<Employee> instance() {
        return Employee::new;
    }
}
