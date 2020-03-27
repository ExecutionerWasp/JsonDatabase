package com.json.database.core.exception;

import com.json.database.core.util.Log;

/**
 * @author Alvin
 **/

public class JsonReaderIsEmptyException extends RuntimeException {

    public JsonReaderIsEmptyException() {
        Log.error("Json reader is empty!");
    }
}
