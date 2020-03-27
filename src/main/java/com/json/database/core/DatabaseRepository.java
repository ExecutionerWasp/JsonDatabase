package com.json.database.core;

import com.json.database.core.entity.Entity;
import com.json.database.core.entity.Repository;
import com.json.database.core.util.JsonBuilder;
import com.json.database.core.util.ReflectionService;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.json.database.core.util.Log.error;
import static com.json.database.core.util.Log.info;

/**
 * @author Alvin
 **/

public interface DatabaseRepository<T extends Entity<ID>, ID extends Serializable> extends DatabaseExecutor<T, ID>, Repository<T, ID> {



    @Override
    default T save(T t) {
        info("Saving object: {}", t);
        Map<String, Object> map = new HashMap<>();
        ReflectionService.fields(instance().get().getClass()).forEach(field -> {
            map.put(field.getName(), ReflectionService.extract(field, t));
        });

        String json = JsonBuilder.OBJECT.of(Map.of("employees", JsonBuilder.LIST.of(List.of(JsonBuilder.OBJECT.of(map)))));
        try {
            write(() -> json);
        } catch (IOException e) {
            error("Database file not found...", e);
        }
        return t;
    }

    @Override
    default T find(ID id) {
        info("Searching object with id: {}", id);
        return null;
    }

    @Override
    default void delete(ID id) {
        info("Deleting object with id: {}", id);
    }

    @Override
    default Iterable<T> all() {
        info("Searching all objects");
        return null;
    }

    @Override
    default Stream<T> stream() {
        info("Generation of empty stream...");
        return Stream.empty();
    }
}
