package com.json.database.util;

import com.json.database.domain.JsonEntityType;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alvin
 **/

public final class ReflectionService {


    public static List<String> fields(Class<?> entity) {
        return Stream.of(entity.getFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    public static Map<String, Object> fieldValueMap(Object entity) {
        final Map<String, Object> map = new HashMap<>();
        final JsonEntityType jsonEntityType = (JsonEntityType) entity;
        map.put("id", jsonEntityType.getId());
        Stream.of(entity.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> !map.containsKey(field.getName()))
                .forEach(field -> {
                    try {
                        map.put(field.getName(), field.get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        field.setAccessible(false);
                    }
                });
        return map;
    }

    public static String buildJson(Object obj) {
        final StringBuilder json = new StringBuilder();
        fieldValueMap(obj).forEach((k, o) -> json
                .append(StringWrap.KEY.wrap(k))
                .append(":")
                .append(StringWrap.VALUE.wrap(o))
                .append(","));
        json.deleteCharAt(json.length() - 1);
        return StringWrap.OBJECT.wrap(json.toString());
    }

    @Deprecated
    public static <T extends JsonEntityType<?>> Optional<T> generateId(T obj) {

        try {
            final Field idField = obj.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            if (idField.getType().equals(Long.class)) {
                idField.set(obj, UUID.randomUUID().timestamp());
                idField.setAccessible(false);
                return Optional.of(obj);
            }
//            if (idField.getType().equals(Integer.class)) {
//                idField.set(obj, UUID.randomUUID().clockSequence());
//                idField.setAccessible(false);
//                return Optional.of(obj);
//            }
//            if (idField.getType().equals(String.class)) {
//                idField.set(obj, UUID.randomUUID().toString());
//                idField.setAccessible(false);
//                return Optional.of(obj);
//            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    public static List<JsonEntityType> parse(List<String> lines, Supplier<? extends JsonEntityType> instance) {
        List<JsonEntityType> result = new ArrayList<>();
        for (String line :
                lines) {
            if (line.contains("{") && line.contains("}")) {
                String[] fields = line.split(",");
                Object object = instance.get();
                for (String field :
                        fields) {
                    String[] params = field.split(":");
                    List<String> cleanParams = new ArrayList<>();
                    for (String param : params) {
                        cleanParams.add(param
                                .replaceAll("(\\[)", "")
                                .replaceAll("(\\])", "")
                                .replaceAll("(\\{)", "")
                                .replaceAll("(\\})", "")
                                .replaceAll("(\")", ""));
                    }
                    try {
                        Field f;
                        if (cleanParams.get(0).equals("id")) {
                            f = object.getClass().getSuperclass().getDeclaredField("id");
                            f.setAccessible(true);
                            f.set(object, NumberFormat.getInstance().parse(cleanParams.get(1)));
                        } else {
                            f = object.getClass().getDeclaredField(cleanParams.get(0));
                            f.setAccessible(true);
                            f.set(object, cleanParams.get(1));
                        }
                    } catch (NoSuchFieldException | IllegalAccessException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                result.add((JsonEntityType) object);
            }
        }
        return result;
    }
}
