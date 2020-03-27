package com.json.database.core.test;


import com.json.database.core.EmployeeDatabase;

/**
 * @author Alvin
 **/

public class RunCore {
    public static void main(String[] args) {
        EmployeeDatabase.INSTANCE.save(new Employee(0L, "NAME"));
    }
}
