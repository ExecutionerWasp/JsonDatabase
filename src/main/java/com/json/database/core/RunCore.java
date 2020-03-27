package com.json.database.core;

/**
 * @author Alvin
 **/

public class RunCore {
    public static void main(String[] args) {
        Database.INSTANCE.init();
        System.out.println(JsonBuilder.LIST.build("sdf","sdf","sdfsdf"));
    }
}
