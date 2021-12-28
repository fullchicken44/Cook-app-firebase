package com.example.cookapp;

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadMeal :onCancelled", error.toException());
            }
        });
    }

    // Fetch all user
    public void fetchAllUser(DatabaseReference userDb, MainActivity.firebaseCallback callback) {
        List<User> userList = new ArrayList<User>();
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    user = userSnapshot.getValue(User.class);
                    userList.add(user);
                }
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

    // Old Update Meals
//    public void updateMeal(String idMeal, String strArea, String strCategory, String strMeal, String strDrinkAlternate,
//                           String strInstructions, String strMealThumb, String strTags, String strYoutube,
//                           String strImageSource, String dateModified, String strIngredient1, String strIngredient2,
//                           String strIngredient3, String strIngredient4, String strIngredient5, String strIngredient6,
//                           String strIngredient7, String strIngredient8, String strIngredient9, String strMeasure1,
//                           String strMeasure2, String strMeasure3, String strMeasure4, String strMeasure5, String strMeasure6,
//                           String strMeasure7, String strMeasure8, String strMeasure9) {
//
//        HashMap Meal = new HashMap();
//        Meal.put("ID Meal", idMeal);
//        Meal.put("Area", strArea);
//        Meal.put("Category: ", strCategory);
//        Meal.put("strMeal: ", strMeal);
//        Meal.put("strDrinkAlternate",strDrinkAlternate);
//        Meal.put("strInstructions",strInstructions);
//        Meal.put("strMealThumb",strMealThumb);
//        Meal.put("strTags",strTags);
//        Meal.put("strYoutube",strYoutube);
//        Meal.put("strImageSource",strImageSource);
//        Meal.put("dateModified",dateModified);
//        Meal.put("strIngredient1",strIngredient1);
//        Meal.put("strIngredient2",strIngredient2);
//        Meal.put("strIngredient3",strIngredient3);
//        Meal.put("strIngredient4",strIngredient4);
//        Meal.put("strIngredient5",strIngredient5);
//        Meal.put("strIngredient6",strIngredient6);
//        Meal.put("strIngredient7",strIngredient7);
//        Meal.put("strIngredient8",strIngredient8);
//        Meal.put("strIngredient9",strIngredient9);
//        Meal.put("strMeasure1",strMeasure1);
//        Meal.put("strMeasure2",strMeasure2);
//        Meal.put("strMeasure3",strMeasure3);
//        Meal.put("strMeasure4",strMeasure4);
//        Meal.put("strMeasure5",strMeasure5);
//        Meal.put("strMeasure6",strMeasure6);
//        Meal.put("strMeasure7",strMeasure7);
//        Meal.put("strMeasure8",strMeasure8);
//        Meal.put("strMeasure9",strMeasure9);
//        databaseReference.updateChildren(Meal);
//    }




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
        void call(User userList);
    }
}

