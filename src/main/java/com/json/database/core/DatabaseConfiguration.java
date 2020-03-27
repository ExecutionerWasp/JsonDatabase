package com.json.database.core;

import com.json.database.core.entity.Entity;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

public interface DatabaseConfiguration<T extends Entity<ID>, ID extends Serializable> {

    Supplier<T> instance();

    String document();

    ID id();

    String url();
}
