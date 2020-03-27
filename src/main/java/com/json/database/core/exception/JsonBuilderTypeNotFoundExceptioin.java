package com.json.database.core.exception;

/**
 * @author Alvin
 **/

public class JsonBuilderTypeNotFoundExceptioin extends RuntimeException {

    private static final String message = "Type must be instance of Map or List...";

    public JsonBuilderTypeNotFoundExceptioin() {
        super(message);
    }

    public JsonBuilderTypeNotFoundExceptioin(Class<?> currentType) {
        super(message.concat("\nCurrent type: ").concat(currentType.getName()));
    }
}
