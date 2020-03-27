package com.json.database.core;

import com.json.database.core.util.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
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
            String s = JsonBuilder.OBJECT.build(
                    Map.of(
                            "document", config().document(),
                            "last_generated_id", 0,
                            "objects", JsonBuilder.LIST.build()
                    ));
            write(() -> s);
        } catch (IOException e) {
            System.out.println("Fold...");
            e.printStackTrace();
        }
    }

    void clean();

    File export();

    void bring(File db);
}
