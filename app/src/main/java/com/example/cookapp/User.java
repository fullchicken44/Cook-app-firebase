package com.example.cookapp;

import java.util.List;

public class User {
    String email, name;
    List<Meal> collection, mealCreate;
    boolean isAdmin;

    public User(String email, String name, List<Meal> collection, List<Meal> mealCreate, boolean isAdmin) {
        this.email = email;
        this.name = name;
        this.collection = collection;
        this.mealCreate = mealCreate;
        this.isAdmin = isAdmin;
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

    public List<Meal> getCollection() {
        return collection;
    }

    public void setCollection(List<Meal> collection) {
        this.collection = collection;
    }

    public List<Meal> getMealCreate() {
        return mealCreate;
    }

    public void setMealCreate(List<Meal> mealCreate) {
        this.mealCreate = mealCreate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
