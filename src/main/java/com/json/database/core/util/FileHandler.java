package com.json.database.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alvin
 **/

public interface FileHandler {

    String type();

    String name();

    Path path();

    default boolean isExists() {
        Path local = Objects.requireNonNull(this.path(), "Path can not be null");
        return Files.exists(local);
    }

    default boolean create() throws IOException {
        if (!isExists()) {
            Files.createFile(this.path());
            return true;
        }
        return false;
    }

    default boolean delete() throws IOException {
        if (isExists()) {
            Files.delete(this.path());
            return true;
        }
        return false;
    }

    default boolean write(Supplier<String> s) throws IOException {
        if (Objects.isNull(s) || Objects.isNull(s.get())) return false;
        if (this.create())
            Files.write(this.path(), s.get().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        else
            Files.write(this.path(), s.get().getBytes(), StandardOpenOption.WRITE);
        return true;
    }

    default Stream<String> read() throws IOException {
        return Files.lines(this.path());
    }

    default Collection<String> read(Supplier<Collection<String>> collection) throws IOException {
        return read().collect(Collectors.toCollection(collection));
    }

    default Optional<String> content() {
        try {
            return Optional.of(new String(Files.readAllBytes(this.path())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
