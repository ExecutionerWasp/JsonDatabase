package com.json.database;

import com.json.database.domain.Database;
import com.json.database.domain.DatabaseConfig;
import com.json.database.domain.JsonEntityType;
import com.json.database.domain.User;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author Alvin
 **/

public class Application {
    public static void main(String[] args) {

        Database.EXECUTE.create(() -> User::new);
        User user = new User("qwerty", "pass");

        Database.EXECUTE.save(new User("login", "pass"));
        user.setId(7L);
        Database.EXECUTE.save(user);
        user = (User) Database.EXECUTE.save(new User("1", "pass")).get();

        System.out.println(Database.EXECUTE.findAll());
        System.out.println(Database.EXECUTE.count());
        System.out.println(Database.EXECUTE.findOne(user.getId()));

        Database.EXECUTE.delete(user);

        System.out.println(Database.EXECUTE.findAll());
        System.out.println(Database.EXECUTE.count());
    }
}
