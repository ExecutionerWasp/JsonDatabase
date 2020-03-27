package com.json.database.core.test;

import com.json.database.core.Database;
import com.json.database.core.util.Log;

/**
 * @author Alvin
 **/

public class RunCore {
    public static void main(String[] args) {
        Database.INSTANCE.init();
        Database.INSTANCE.clean();

        Log.log(System.Logger.Level.INFO, "ksjd");
        Log.log(System.Logger.Level.WARNING, "ksjd");
        Log.log(System.Logger.Level.ERROR, "ksjd");
        Log.log(System.Logger.Level.DEBUG, "ksjd");
        Log.log(System.Logger.Level.TRACE, "ksjd");
        Log.log(System.Logger.Level.ALL, "ksjd");
        Log.log(System.Logger.Level.OFF, "ksjd");
    }
}
