package com.json.database.util;

/**
 * @author Alvin
 **/

public enum StringWrap {

    OBJECT, KEY, VALUE, ARRAY;

    public String wrap(Object obj) {
        final StringBuilder w = new StringBuilder();
        switch (this) {

            case OBJECT:
                return w.append("{").append(obj.toString()).append("}").toString();
            case KEY:
                return w.append("\"").append(obj.toString()).append("\"").toString();
            case VALUE:
                return obj instanceof Number ? w.append(obj.toString()).toString() : KEY.wrap(obj);
            case ARRAY:
                return w.append("[").append(obj.toString()).append("\n]").toString();
        }
        return obj.toString();
    }

}
