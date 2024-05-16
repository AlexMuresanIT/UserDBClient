package com.exercise.UserDBClient.model;

public class User {

    Long id;
    String name;
    String email;
    String password;
    String town;

    public User(Long id, String name, String email, String password, String town) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.town = town;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
