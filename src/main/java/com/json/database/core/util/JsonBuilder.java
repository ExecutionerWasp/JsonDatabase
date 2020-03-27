package com.json.database.core.util;

import com.json.database.core.exception.JsonBuilderTypeNotFoundExceptioin;

import java.util.*;
import java.util.function.Function;

/**
 * @author Alvin
 **/

public enum JsonBuilder implements JsonBuilderService {

    OBJECT(JsonBuilderService.toObject()),
    LIST(JsonBuilderService.toList());

    private Function<Object, String> action;

    JsonBuilder(Function<Object, String> action) {
        this.action = action;
    }

    @Override
    public String of(Object... obj) {
        List<Object> list = Arrays.asList(obj);
        switch (this) {
            case OBJECT:
                return of(Map.of("list", list));
            case LIST:
                return of(list);
        }
        throw new JsonBuilderTypeNotFoundExceptioin(obj.getClass());
    }

    @Override
    public String of(Object obj) {
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
}
