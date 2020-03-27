package com.json.database.core;

import com.json.database.core.util.FileHandler;
import com.json.database.core.util.JsonBuilder;
import com.json.database.core.util.JsonReaderService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static com.json.database.core.util.Log.error;
import static com.json.database.core.util.Log.info;

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
        info("Creating database...");
        try {
            create();
            String s = JsonBuilder.OBJECT.of(
                    Map.of(
                            "document", config().document(),
                            "last_generated_id", 0,
                            "objects", JsonBuilder.LIST.of()
                    ));
            write(() -> s);
            info("Created document: {}", config().document());
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
