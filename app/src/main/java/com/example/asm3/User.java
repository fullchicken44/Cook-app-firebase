package com.example.asm3;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email, name, password;
    private List<String> savedDish;


    public User() {
        this.email = null;
        this.name = null;
        this.password = null;
        this.savedDish = new ArrayList<>();
    }

    public User(String email, String name, String password, List<String> savedDish) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.savedDish = savedDish;
    }

    public void addDish(String name){
        savedDish.add(name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSavedDish() {
        return savedDish;
    }

    public void setSavedDish(List<String> savedDish) {
        this.savedDish = savedDish;
    }
}
