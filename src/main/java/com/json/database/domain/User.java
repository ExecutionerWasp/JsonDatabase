package com.json.database.domain;

/**
 * @author Alvin
 **/

public class User extends JsonEntityType<Long> {

    private String login;
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() + '\'' +
                ",login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
