package com.example.asm3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityUserPage extends AppCompatActivity {
    FirebaseDB firebaseHandler = new FirebaseDB();
    List<User> mainUserList = new ArrayList<User>();

    private CircleImageView imageAdd;
    private Uri imageUri;
    private static final  int IMAGE_REQUEST = 2;

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

            imageAdd = findViewById(R.id.imageView);

            imageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //openImage();
                    AlertDialog alert = new AlertDialog.Builder(ActivityUserPage.this).create();
                    // alert.setView(mealObjImage);, causing crash, fix later
                    alert.setTitle("Change profile picture");
                    alert.setMessage("Click upload to upload a new image!");
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "Upload",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    openImage();
                                }
                            });

                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alert.dismiss();
                                }
                            });

                    alert.show();

                }
            });

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

    private void openImage() {

        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();

            uploadImage();
        }
    }

    private String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            Log.d("DownloadUrl", url);

                            pd.dismiss();
                            Toast.makeText(ActivityUserPage.this, "Image upload successful1", Toast.LENGTH_SHORT).show();

                            // After upload image then fetch into the profile picture
                            Picasso.get()
                                    .load(url)
                                    .centerCrop()
                                    .fit()
                                    .into(imageAdd);
                        }
                    });
                }
            });
        }
    }

    public interface firebaseCallback {
        void call(List list);
    }
}