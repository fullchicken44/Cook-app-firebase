package com.example.cookapp;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDB {
    private Meal meal;
    private final String dbAPI = "https://android-2a378-default-rtdb.asia-southeast1.firebasedatabase.app/";

    public void fetchAllMeal(DatabaseReference mealDb, MainActivity.firebaseCallback callback) {
        List<Meal> mealList = new ArrayList<Meal>();
        mealDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot mealSnapshot: snapshot.getChildren()) {
                    meal = mealSnapshot.getValue(Meal.class);
                    mealList.add(meal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadMeal :onCancelled", error.toException());
            }
        });
    }

    public Meal fetchMealById(List<Meal> mealList, String id) {
        Meal mealFound = new Meal();
        for (int i = 0; i < mealList.size();i++) {
            if (id.equals(mealList.get(i).idMeal)) {
                mealFound = mealList.get(i);
                break;
            }
        }
        return mealFound;
    }

    public void postMeal(Meal meal, DatabaseReference mealDb) {
        mealDb.setValue(meal);
    }

    public void printMealTest(Meal meal) {
        System.out.println("idmeal: " + meal.getIdMeal());
        System.out.println("meal name: " + meal.getStrMeal());
        System.out.println("category: " + meal.getStrCategory());
        System.out.println("area: " + meal.getStrArea());
        System.out.println("Instruction: " + meal.getStrInstructions());
        System.out.println("meal thumb: " + meal.getStrMealThumb());
        System.out.println("tags: " + meal.getStrTags());
        System.out.println("youtube: " + meal.getStrYoutube());
        System.out.println("ingredient1: " + meal.getStrIngredient1());
        System.out.println("measure2: " + meal.getStrMeasure2());
    }

    public interface firebaseCallback {
        void call(Meal mealList);
    }
}

