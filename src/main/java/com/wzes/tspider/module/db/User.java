package com.wzes.tspider.module.db;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class User {
    private String user_id;
    private String password;
    private String email;
    private int id;
    private String username;

    public User() {
    }

    public User(String user_id, String password, String email, String username) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
