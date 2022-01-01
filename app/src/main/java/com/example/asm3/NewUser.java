package com.example.asm3;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class NewUser {
    String email, name;
    List<Meal> collection, mealCreate;
    //    String collection;
//    String mealCreate;
    boolean isAdmin;

    public NewUser() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public NewUser(String email, String name, List<Meal> collection, List<Meal> mealCreate, boolean isAdmin) {
        this.email = email;
        this.name = name;
        this.collection = collection;
        this.mealCreate = mealCreate;
        this.isAdmin = isAdmin;
    }

    // Update specific fields
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", name);
        result.put("collection", collection);
        result.put("mealCreate", mealCreate);
        result.put("isAdmin", isAdmin);

        return result;
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

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", collection=" + collection +
                ", mealCreate=" + mealCreate +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
