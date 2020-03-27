package com.json.database.core;

import com.json.database.core.util.FileHandler;
import com.json.database.core.util.JsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author Alvin
 **/

public interface DatabaseExecutor extends FileHandler {

    DatabaseConfiguration<?, ?> config();

    @Override
    default String type() {
        return ".json";
    }

    @Override
    default String name() {
        return config().document();
    }

    @Override
    default Path path() {
        return Path.of(config().url().concat(config().document()).concat(type()));
    }



    default void init() {
        System.out.println("Creating database...");
        try {
            create();
            String s = JsonBuilder.OBJECT.of(
                    Map.of(
                            "document", config().document(),
                            "last_generated_id", 0,
                            "objects", JsonBuilder.LIST.of()
                    ));
            write(() -> s);
        } catch (IOException e) {
            System.out.println("Fold...");
            e.printStackTrace();
        }
    }

    default void clean() {
        System.out.println("Cleaning database...");

    }

    default File export() {
        System.out.println("Exporting database...");
        return path().toFile();
    }

    default void bring(File db) {
        System.out.println("Importing database...");
    }
}
