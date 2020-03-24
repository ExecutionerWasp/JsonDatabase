package com.json.database.domain;

import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

@FunctionalInterface
public interface DatabaseConfig {

    Supplier<? extends JsonEntityType> createInstance();

    default String name() {
        return createInstance().get().getClass().getSimpleName().toLowerCase();
    }

    default String url() {
        return name().concat(".json");
    }

    default Path path() {
        return Path.of(url());
    }
}
