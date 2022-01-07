package com.example.asm3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserPage extends AppCompatActivity {
    FirebaseDB firebaseHandler = new FirebaseDB();
    List<User> mainUserList = new ArrayList<User>();
    User user;
    //private final String dbAPI = "https://android-2a378-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private final String dbAPI = "https://s3777242androidfinal-default-rtdb.firebaseio.com/";
    DatabaseReference userDb = FirebaseDatabase.getInstance(dbAPI).getReference("users").child("users");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        // Users
        firebaseHandler.fetchAllUser(userDb, userList -> {
            for (int i = 0; i < userList.size();i++) {
                user = (User) userList.get(i);
                mainUserList.add(user);
            }

            Log.d("TAG", "User trong day la: " + mainUserList.get(3).toString());

            // Current user position
            // Get one object
            User userObj = mainUserList.get(3);

            TextView userHeaderTV = (TextView) findViewById(R.id.textView);
            userHeaderTV.setText("HELLO "+userObj.getUserName());
            userHeaderTV.setSelected(true);
            userHeaderTV.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            userHeaderTV.setSingleLine(true);

            // Get all meals by user
            ListView userMealsTB;
            List<String> userMeal = new ArrayList<>();
            for (int i =0; i <=2; i++){
                userMeal.add(userObj.getCollection().get(i));
            }

            Log.d("TAG", "Meal cua user la: " + userMeal.toString());


            ListView userSaveMealsTB;
            List<String> userSaveMeal = new ArrayList<>();
            for (int i =0; i <=2; i++){
                userSaveMeal.add(userObj.getMealCreate().get(i));
            }

            Log.d("TAG", "Meal da tao cua user la: " + userSaveMeal.toString());

//            ImageButton btnBack = null;
//            btnBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.i(TAG, "Click on back button: ");
//                    Toast.makeText(getApplicationContext(),"Home Button",Toast.LENGTH_LONG).show();
//                    //Intent intent = new Intent(MainActivity.this, ActivityUserPage.class);
//                    //startActivity(intent);
//                }
//
//            });

        });
    }

    public interface firebaseCallback {
        void call(List list);
    }
}