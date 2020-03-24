package com.json.database.domain;

import com.json.database.repository.Repository;
import com.json.database.util.ReflectionService;
import com.json.database.util.StringWrap;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * @author Alvin
 **/
public enum Database implements Repository {
    EXECUTE;

    private DatabaseConfig databaseConfig = null;

    public boolean create(DatabaseConfig databaseConfig) {
        if (Objects.isNull(this.databaseConfig)) {
            this.databaseConfig = databaseConfig;
            try {
                Files.createFile(databaseConfig.path());
            } catch (IOException e) {
                System.out.println("File already exists...");
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Optional<? extends JsonEntityType> save(Object entity) {
        write(ReflectionService.buildJson(entity));
        return Optional.of((JsonEntityType) entity);
    }

    @Override
    public Optional findOne(Long primaryKey) {
        List<? extends JsonEntityType> jsons = findAll();
        for (JsonEntityType t :
                jsons) {
            if (t.getId() == primaryKey) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<? extends JsonEntityType> findAll() {
        return ReflectionService.parse(lines(), databaseConfig.createInstance());
    }

    @Override
    public Long count() {
        return (long) (int) lines().stream().filter(s -> s.length() > 5).count();
    }

    @Override
    public void delete(Object entity) {
        JsonEntityType obj = (JsonEntityType) entity;
        if (exists(obj.getId())) {
            List<? extends JsonEntityType> objects = findAll();
            Integer index = null;
            for (int i = 0; i < objects.size(); i++) {
                if (obj.getId() == objects.get(i).getId()) {
                    index = i;
                }
            }
            if (Objects.nonNull(index)) {
                objects.remove((int) index);
            }
            try {
                Files.delete(this.databaseConfig.path());
                if (objects.size() != 0)
                    objects.forEach(this::save);
            } catch (IOException e) {
                System.out.println("Database is not exists...");
            }
        }
    }

    @Override
    public boolean exists(Long primaryKey) {
        List<? extends JsonEntityType> jsons = findAll();
        for (JsonEntityType t :
                jsons) {
            if (t.getId() == primaryKey) {
                return true;
            }
        }
        return false;
    }

    private List<String> lines() {
        try {
            return Files.readAllLines(databaseConfig.path());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private String write(String s) {
        Objects.requireNonNull(s);
        final StringBuilder data = new StringBuilder();
        try {
            if (Files.notExists(databaseConfig.path())) {
                Files.write(databaseConfig.path(),
                        StringWrap.ARRAY.wrap(data.append("\n").append(s)).getBytes(), CREATE, WRITE);
                return data.toString();
            }
            final String oldData = new String(Files.readAllBytes(databaseConfig.path()));
            if (oldData.equals("")) {
                Files.write(databaseConfig.path(),
                        StringWrap.ARRAY.wrap(
                                data.append("\n").append(s)).getBytes(), WRITE);
                return data.toString();
            }
            data
                    .append(oldData, 1, oldData.length() - 1)
                    .append(",\n");

            Files.write(databaseConfig.path(),
                    StringWrap.ARRAY.wrap(
                            data.append(s)).getBytes(), WRITE);
        } catch (IOException e) {
            if (Files.notExists(databaseConfig.path())) {
                try {
                    Files.createFile(databaseConfig.path());
                    return write(s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return data.toString();
    }
}