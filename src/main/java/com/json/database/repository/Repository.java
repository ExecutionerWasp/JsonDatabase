package com.json.database.repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Alvin
 **/

public interface Repository<T> {

    <S extends T> Optional<S> save(Object entity);

    Optional<T> findOne(Long primaryKey);

    List<T> findAll();

    Long count();

    void delete(T entity);

    boolean exists(Long primaryKey);
}
