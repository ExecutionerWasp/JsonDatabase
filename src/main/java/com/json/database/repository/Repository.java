package com.json.database.repository;

import com.json.database.domain.JsonEntityType;

import java.util.List;
import java.util.Optional;

/**
 * @author Alvin
 **/

public interface Repository<ID extends Long, T extends JsonEntityType<ID>> {

    <S extends T> Optional<S> save(T entity);

    Optional<T> findOne(ID primaryKey);

    List<T> findAll();

    ID count();

    void delete(T entity);

    boolean exists(ID primaryKey);
}
