package com.json.database.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static com.json.database.core.util.Log.*;

/**
 * @author Alvin
 **/

public final class ReflectionService {

    public static <T> T create(Supplier<Class<T>> classSupplier) {
        if (Objects.isNull(classSupplier)) warn("Supplier, is null", NullPointerException::new);
        return create(classSupplier.get());
    }

    public static <T> T create(Class<T> clazz) {
        info("Try to create object by class: {}", clazz);
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            error("Instance generation has been fallen", e);
        }
        info("Expound object state: {}", obj);
        return obj;
    }

    public static Field field(String name, Class<?> clazz) {
        info("Try to get field with name: {} of class: {}", name, clazz);
        if (Objects.isNull(clazz)) warn("Class: {}, is null", NullPointerException::new);
        if (Objects.isNull(name)) warn("Field name: {}, is null", NullPointerException::new);

        Field field = null;

        try {
            field = clazz.getField(name);
        } catch (NoSuchFieldException e) {
            error("Field consuming has been fallen.", e);
        }

        info("Expound field state: {}", field);
        return field;
    }

    public static List<Field> fields(Class<?> clazz) {
        Log.info("Try to get fields of: {}", clazz);
        if (Objects.isNull(clazz)) warn("Class: {}, is null", NullPointerException::new);

        List<Field> fields = new ArrayList<>();
        for (Field f :
                clazz.getFields()) {
            f.setAccessible(true);
            fields.add(f);
        }
        for (Field f :
                clazz.getDeclaredFields()) {
            f.setAccessible(true);
            fields.add(f);
        }
        return fields;
    }

    public static Object introduce(Object value, Field field, Object instance) {
        info("Try to introduce value into field: {} of instance: {}",
                field.getName(), instance.getClass().getSimpleName());
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            error("Introducing has been fallen ...", e);
        }
        info("Introduced value: {}", value);
        return value;
    }

    public static Object extract(Field field, Object instance) {
        info("Try to extract value from field: {} of instance: {}",
                field.getName(), instance.getClass().getSimpleName());
        Object value = null;
        try {
            value = field.get(instance);
        } catch (IllegalAccessException e) {
            error("Extracting has been fallen ...", e);
        }
        info("Extracted value: {}", value);
        return value;
    }
}
