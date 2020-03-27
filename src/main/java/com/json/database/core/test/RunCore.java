package com.json.database.core.test;

import com.json.database.core.Database;

/**
 * @author Alvin
 **/

public class RunCore {
    public static void main(String[] args) {
        Database.INSTANCE.init();
        Database.INSTANCE.clean();
    }
}
