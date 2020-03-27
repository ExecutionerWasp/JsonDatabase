package com.json.database.core.exception;

import com.json.database.core.util.Log;

/**
 * @author Alvin
 **/

public class JsonBuilderTypeNotFoundException extends RuntimeException {

    private static final String message = "Type must be instance of Map or List...";

    public JsonBuilderTypeNotFoundException() {
        Log.error(message);
    }

    public JsonBuilderTypeNotFoundException(Class<?> currentType) {
        String message = JsonBuilderTypeNotFoundException.message.concat("\nCurrent type: ").concat(currentType.getName());
        Log.error(message);
    }
}
