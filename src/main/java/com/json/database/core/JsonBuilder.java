package com.json.database.core;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

public enum JsonBuilder {

    OBJECT(o -> {
        Map<?, ?> fields = (Map<?, ?>) o;
        if (fields.isEmpty()) {
            return "{}";
        }
        StringBuilder local = new StringBuilder();
        local.append("{\n");
        fields.forEach((key, val) -> {
            local.append("\"").append(key).append("\"")
                    .append(":");
            if (isObject(val)) {
                local.append(val).append(",\n");
            } else {
                local.append("\"").append(val).append("\"").append(",\n");

            }
        });
        local.delete(local.length() - 2, local.length() - 1);
        local.append("}");
        return local.toString();
    }),

    LIST(o -> {
        List<?> list = (List<?>) o;
        if (list.isEmpty()) {
            return "[]";
        }
        StringBuilder local = new StringBuilder();
        local.append("[\n");
        list.forEach(obj -> {
            String val = obj.toString();
            if (isObject(val)) {
                local.append(val).append(",\n");
            } else {
                local.append("\"").append(val).append("\"").append(",\n");
            }
        });
        local.delete(local.length() - 2, local.length() - 1);
        local.append("]");
        return local.toString();
    });

    private Function<Object, String> action;

    JsonBuilder(Function<Object, String> action) {
        this.action = action;
    }

    public String build(Object... obj) {
        List<Object> list = Arrays.asList(obj);
        switch (this) {
            case OBJECT:
                return build(Map.of("list", list));
            case LIST:
                return build(list);
        }
        throw new JsonBuilderTypeNotFoundExceptioin(obj.getClass());
    }

    public String build(Supplier<?> obj) {
        Objects.requireNonNull(obj);
        return build(obj.get());
    }

    public String build(Object obj) {
        Objects.requireNonNull(obj);
        switch (this) {
            case OBJECT:
                if (obj instanceof Map) {
                    return this.action.apply(obj);
                }
            case LIST:
                if (obj instanceof List) {
                    return this.action.apply(obj);
                }
        }
        throw new JsonBuilderTypeNotFoundExceptioin(obj.getClass());
    }

    public static boolean isObject(Object obj) {
        return isObject(obj.toString())
                || obj instanceof Number;
    }

    private static boolean isObject(String s) {
        return s.contains("[") &&
                s.contains("]") ||
                s.contains("{") &&
                s.contains("}");
    }
}
