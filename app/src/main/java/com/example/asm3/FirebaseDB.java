package com.example.asm3;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseDB {
    // Add class
    private Meal meal;
    private User user;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUser;
    private DatabaseReference mReferenceMeal;
    private DatabaseReference mReferenceUserSaveList;

    // Interface

    public interface DataStatus{
        void DataIsLoaded(List<User> user, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public void FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceUser = mDatabase.getReference("users").child("users");
        mReferenceUserSaveList = mDatabase.getReference("users").child("users").child("collection");
        mReferenceMeal = mDatabase.getReference("meals");
    }

    // API
    private final String dbAPI = "https://android-2a378-default-rtdb.asia-southeast1.firebasedatabase.app/";

    // Fetch all meals
    public void fetchAllMeal(DatabaseReference mealDb, MainActivity.firebaseCallback callback) {
        List<Meal> mealList = new ArrayList<Meal>();
        mealDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot mealSnapshot: snapshot.getChildren()) {
                    meal = mealSnapshot.getValue(Meal.class);
                    mealList.add(meal);
                }
                callback.call(mealList);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadMeal :onCancelled", error.toException());
            }
        });
    }

    // Fetch all user
    public void fetchAllUser(DatabaseReference userDb, ActivityUserPage.firebaseCallback callback) {
        List<User> userList = new ArrayList<User>();
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    user = userSnapshot.getValue(User.class);
                    userList.add(user);
                }
                callback.call(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadUser :onCancelled", error.toException());
            }
        });
    }

    // Post User
    public void postUser(User user, final DataStatus dataStatus) {
        String key = mReferenceUser.push().getKey();
        mReferenceUser.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsInserted();
            }
        });
    }

    // Update Meals
    public void updateMeal(String key, Meal meal, final DataStatus dataStatus) {
        mReferenceMeal.child(key).setValue(meal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsUpdated();
            }

        });
    }

    // Delete Meals
    public void deleteMeal(String key, final DataStatus dataStatus){
        mReferenceMeal.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsDeleted();
            }
        });
    }

    // Update User Save Lists

    public void updateUsersSaveLists(String key, User user, final DataStatus dataStatus) {
        mReferenceUserSaveList.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsUpdated();
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

    public void postMeal(Meal meal, DatabaseReference mealDb, int index) {
        mealDb.child(String.valueOf(index)).setValue(meal);
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
        void call(List mealList);
    }
}

