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
    public static void main(String[] args) throws IOException {

        Database.EXECUTE.create(() -> User::new);
        User user;

        user = (User) Database.EXECUTE.save(new User("1", "pass")).get();
        Database.EXECUTE.save(new User("login", "pass"));
        Database.EXECUTE.save(new User("qwerty", "pass"));

        System.out.println(Database.EXECUTE.findAll());
        System.out.println(Database.EXECUTE.count());
        System.out.println(Database.EXECUTE.findOne(user.getId()));

        Database.EXECUTE.delete(user);

        System.out.println(Database.EXECUTE.findAll());
        System.out.println(Database.EXECUTE.count());
    }
}