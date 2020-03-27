package com.json.database.core;

import com.json.database.core.entity.Entity;
import com.json.database.core.util.FileHandler;
import com.json.database.core.util.JsonBuilder;
import com.json.database.core.util.JsonReaderService;
import com.json.database.core.util.ReflectionService;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.json.database.core.util.Log.*;

/**
 * @author Alvin
 **/

public interface DatabaseExecutor<T extends Entity<ID>, ID extends Serializable> extends FileHandler, DatabaseConfiguration<T, ID> {


    @Override
    @SuppressWarnings("unchecked")
    default Supplier<T> instance() {
        return (Supplier<T>) ReflectionService.create(Entity.class);
    }

    @Override
    default String document() {
        return instance().get().getClass().getSimpleName().toLowerCase();
    }

    @Override
    default Optional<ID> id() {
        warn("Default id generator is empty!!!");
        return Optional.empty();
    }

    @Override
    default String url() {
        return "";
    }

    @Override
    default String type() {
        return ".json";
    }

    @Override
    default String name() {
        return document();
    }

    @Override
    default Path path() {
        return Path.of(url().concat(document()).concat(type()));
    }


    default void init() {
        info("Creating database...");
        try {
            create();
            String s = JsonBuilder.OBJECT.of(
                    Map.of(
                            "document", document(),
                            "last_generated_id", 0,
                            "objects", JsonBuilder.LIST.of()
                    ));
            write(() -> s);
            info("Created document: {}", document());
        } catch (IOException e) {
            error("Fold...");
            e.printStackTrace();
        }
    }

    default void clean() {
        info("Cleaning database...");
        JsonReaderService<String> service = this::content;
        service.extract("document");
    }

    default File export() {
        info("Exporting database...");
        return path().toFile();
    }

    default void bring(File db) {
        info("Importing database...");
    }
}
