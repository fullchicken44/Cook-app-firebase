package com.example.cookapp;

import androidx.appcompat.app.AppCompatActivity;
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

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final String dbAPI = "https://android-2a378-default-rtdb.asia-southeast1.firebasedatabase.app/";
    DatabaseReference mealDb = FirebaseDatabase.getInstance(dbAPI).getReference("meals");
    List<Meal> mainMealList = new ArrayList<Meal>();
    int countMeal = 0;
    Meal meal;
    FirebaseDB firebaseHandler = new FirebaseDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseHandler.fetchAllMeal(mealDb, mealList -> {
            mainMealList = (List<Meal>) mealList;
        });
        System.out.println(mainMealList);
//        firebaseHandler.postMeal(mealList.get(5), mealDb);

    }

    public interface firebaseCallback {
        void call(Meal mealList);
    }

}