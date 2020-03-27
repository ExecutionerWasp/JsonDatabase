package com.json.database.core.test;

import com.json.database.core.Database;
import com.json.database.core.util.JsonBuilder;

/**
 * @author Alvin
 **/

public class RunCore {
    public static void main(String[] args) {
        Database.INSTANCE.init();
        System.out.println(JsonBuilder.LIST.of("sdf","sdf","sdfsdf"));
    }
}
