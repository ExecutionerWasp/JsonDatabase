package com.json.database.core.entity;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author Alvin
 **/

public interface EntityExecutor<ID extends Serializable, T extends Entity<ID>> {

    T save(T t);
    T find(ID id);
    void delete(ID id);

    Iterable<T> all();

    Stream<T> stream();
}
