package com.json.database.core.util;

import com.json.database.core.exception.JsonReaderIsEmptyException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static com.json.database.core.util.Log.info;

/**
 * @author Alvin
 **/

@FunctionalInterface
public interface JsonReaderService<J extends String> {

    Optional<J> json();

    default <O extends String> O extract(String key){
        J j = json().orElseThrow(JsonReaderIsEmptyException::new);
        info("Extracting key: {}", key);
        if (Objects.nonNull(key)){
            List<String> keys = Arrays.asList(j.split(key));
            info("Extracted keys: {}", key);
        }
        return (O) "";
    }

    default boolean isEmpty() {
        return this.json().isEmpty();
    }

    static JsonReaderService<? extends String> empty(){
        return ()-> Optional.of("");
    }

    static JsonReaderService<? extends String> of(String json){
        return Objects.nonNull(json) ?
                () -> Optional.of(json) :
                JsonReaderService.empty();
    }

    static JsonReaderService<? extends String> of(Supplier<? extends String> json){
        return Objects.nonNull(json) && Objects.nonNull(json.get()) ?
                (JsonReaderService<? extends String>) json :
                JsonReaderService.empty();
}
}
