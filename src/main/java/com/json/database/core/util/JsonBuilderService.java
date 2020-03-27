package com.json.database.core.util;

import com.json.database.core.exception.JsonBuilderTypeNotFoundExceptioin;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

@FunctionalInterface
public interface JsonBuilderService {

    String of(Object... obj);

    default String of(Supplier<?> obj) {
        Objects.requireNonNull(obj);
        return of(obj.get());
    }

    default String of(Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof Map) {
            return toObject().apply(obj);
        }
        if (obj instanceof List) {
            return toList().apply(obj);
        }
        throw new JsonBuilderTypeNotFoundExceptioin(obj.getClass());
    }

    static Function<Object, String> toList() {
        return o -> {
            List<?> list = (List<?>) o;
            if (list.isEmpty()) {
                return "[]";
            }
            StringBuilder local = new StringBuilder();
            local.append("[\n");
            list.forEach(val -> {
                if (JsonBuilderService.isObject(val)) {
                    local.append(val).append(",\n");
                } else {
                    local.append("\"").append(val).append("\"").append(",\n");
                }
            });
            local.delete(local.length() - 2, local.length() - 1);
            local.append("]");
            return local.toString();
        };
    }

    static Function<Object, String> toObject() {
        return o -> {
            Map<?, ?> fields = (Map<?, ?>) o;
            if (fields.isEmpty()) {
                return "{}";
            }
            StringBuilder local = new StringBuilder();
            local.append("{\n");
            fields.forEach((key, val) -> {
                local.append("\"").append(key).append("\"")
                        .append(":");
                if (JsonBuilderService.isObject(val)) {
                    local.append(val).append(",\n");
                } else {
                    local.append("\"").append(val).append("\"").append(",\n");

                }
            });
            local.delete(local.length() - 2, local.length() - 1);
            local.append("}");
            return local.toString();
        };
    }

    static boolean isObject(Object obj) {
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
