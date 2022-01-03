package com.example.asm3;

import androidx.appcompat.app.AppCompatActivity;
import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.asm3.FirebaseDB;
import com.example.asm3.Meal;
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
//    private final String dbAPI = "https://s3777242androidfinal-default-rtdb.firebaseio.com/";

    DatabaseReference mealDb = FirebaseDatabase.getInstance(dbAPI).getReference("meals");
    DatabaseReference userDb = FirebaseDatabase.getInstance(dbAPI).getReference("user").child("users");
    List<Meal> mainMealList = new ArrayList<Meal>();
    List<User> mainUserList = new ArrayList<User>();
    int countMeal = 0;
    Meal meal, meal1;
    User user;
    FirebaseDB firebaseHandler = new FirebaseDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Meals
        firebaseHandler.fetchAllMeal(mealDb, mealList -> {
            for (int i = 0; i < mealList.size();i++) {
                meal = (Meal) mealList.get(i);
                mainMealList.add(meal);
            }

            Log.d("TAG", "Meal trong day la: " + mainMealList.get(5).toString());

        });

        // Users
        firebaseHandler.fetchAllUser(userDb, userList -> {
            for (int i = 0; i < userList.size();i++) {
                user = (User) userList.get(i);
                mainUserList.add(user);
            }

            Log.d("TAG", "User trong day la: " + mainUserList.get(5).toString());

        });

    }


    public interface firebaseCallback {
        void call(List list);
    }


}