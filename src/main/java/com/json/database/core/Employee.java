package com.json.database.core;

/**
 * @author Alvin
 **/

public class Employee implements Entity<Long> {

    private Long id;
    private String name;

    public Employee() {
    }

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
