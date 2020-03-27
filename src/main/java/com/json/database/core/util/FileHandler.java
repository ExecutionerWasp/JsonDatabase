package com.json.database.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.json.database.core.util.Log.info;
import static com.json.database.core.util.Log.warn;


/**
 * @author Alvin
 **/

public interface FileHandler {

    String type();

    String name();

    Path path();

    default boolean isExists() {
        Path local = Objects.requireNonNull(this.path(), () -> {
            warn("File is not exists...");
            return "";
        });
        info("File: {}, is exists...", local);
        return Files.exists(local);
    }

    default boolean create() throws IOException {
        info("Try to create file...");
        if (!isExists()) {
            Files.createFile(this.path());
            info("Created.");
            return true;
        }
        warn("File not created.");
        return false;
    }

    default boolean delete() throws IOException {
        info("Deleting file: {}", path());
        if (isExists()) {
            Files.delete(this.path());
            info("Deleting success...");
            return true;
        }
        warn("Deleting fold...");
        return false;
    }

    default boolean write(Supplier<String> s) throws IOException {
        info("Writing file...");
        if (Objects.isNull(s) || Objects.isNull(s.get())) {
            warn("Fold...");
            warn("Supplied: {}", s);
            return false;
        }
        info("Writing string: {}", s.get());
        if (this.create())
            Files.write(this.path(), s.get().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        else
            Files.write(this.path(), s.get().getBytes(), StandardOpenOption.WRITE);
        info("Has been write.");
        return true;
    }

    default Stream<String> read() throws IOException {
        info("Read lines from file...");
        return Files.lines(this.path());
    }

    default Collection<String> read(Supplier<Collection<String>> collection) throws IOException {
        return read().collect(Collectors.toCollection(collection));
    }

    default Optional<String> content() {
        info("Get content from file as single string...");
        try {
            return Optional.of(new String(Files.readAllBytes(this.path())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
