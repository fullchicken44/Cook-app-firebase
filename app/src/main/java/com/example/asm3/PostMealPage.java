package com.example.asm3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostMealPage extends AppCompatActivity {
    FirebaseDB firebaseHandler = new FirebaseDB();
    List<User> mainUserList = new ArrayList<User>();
    User user;
    Meal meal = new Meal();
    private EditText thumbUrl;
    String dishNameStr;
    //private final String dbAPI = "https://android-2a378-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private final String dbAPI = "https://s3777242androidfinal-default-rtdb.firebaseio.com/";
    DatabaseReference testuserDb = FirebaseDatabase.getInstance(dbAPI).getReference("testusers").child("users");
    DatabaseReference mealDb = FirebaseDatabase.getInstance(dbAPI).getReference("meal");

    private Button imageAdd;
    private Uri imageUri;
    private static final  int IMAGE_REQUEST = 2;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_meal_page);

        imageAdd = findViewById(R.id.input_image_post_meal);


        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
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

                            meal.setStrMealThumb(url);

                            pd.dismiss();
                            Toast.makeText(PostMealPage.this, "Image upload successful1", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    // Post on meal unofficial
    private void onClickPushData() {
        DatabaseReference mealUnofficial = FirebaseDatabase.getInstance(dbAPI).getReference("mealunofficial");
        TextView mealName = findViewById(R.id.input_name_post_meal);
        meal.setStrMeal(mealName.getText().toString());

        TextView mealCategories = findViewById(R.id.input_category_post_meal);
        meal.setStrCategory(mealCategories.getText().toString());

        TextView mealTag = findViewById(R.id.input_tag_post_meal);
        meal.setStrTags(mealTag.getText().toString());

        TextView mealArea = findViewById(R.id.input_area_post_meal);
        meal.setStrArea(mealArea.getText().toString());

        TextView strDrinkAlternate = findViewById(R.id.input_drink_post_meal);
        meal.setStrDrinkAlternate(strDrinkAlternate.getText().toString());

        TextView mealImageURL = findViewById(R.id.input_image_post_meal);
        meal.setStrImageSource(mealImageURL.getText().toString());

        TextView mealYoutubeURL = findViewById(R.id.input_youtube_post_meal);
        meal.setStrYoutube(mealYoutubeURL.getText().toString());

        TextView strIngredient = findViewById(R.id.input_ingredient_post_meal);
        String stringIngredient = strIngredient.getText().toString();
        ingredientHandler(stringIngredient);

        TextView strMeasurePost = findViewById(R.id.input_measure_post_meal);
        String stringMeasure = strMeasurePost.getText().toString();
        measureHandler(stringMeasure);

        mealDb.setValue(meal);

        //Meal meal = new Meal("123456", "Area51", "A", "Name", "B");
    }

    public void ingredientHandler(String str) {
        List<String> ingredientList = new ArrayList<String>();
        String[] arrOfStr = str.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {
            ingredientList.add(arrOfStr[i]);
        }
        for (int i = arrOfStr.length; i < 19; i++) {
            ingredientList.add(null);
        }

        meal.setStrIngredient1(ingredientList.get(0));
        meal.setStrIngredient2(ingredientList.get(1));
        meal.setStrIngredient3(ingredientList.get(2));
        meal.setStrIngredient4(ingredientList.get(3));
        meal.setStrIngredient5(ingredientList.get(4));
        meal.setStrIngredient6(ingredientList.get(5));
        meal.setStrIngredient7(ingredientList.get(6));
        meal.setStrIngredient8(ingredientList.get(7));
        meal.setStrIngredient9(ingredientList.get(8));
        meal.setStrIngredient10(ingredientList.get(9));
        meal.setStrIngredient11(ingredientList.get(10));
        meal.setStrIngredient12(ingredientList.get(11));
        meal.setStrIngredient13(ingredientList.get(12));
        meal.setStrIngredient14(ingredientList.get(13));
        meal.setStrIngredient15(ingredientList.get(14));
        meal.setStrIngredient16(ingredientList.get(15));
        meal.setStrIngredient17(ingredientList.get(16));
        meal.setStrIngredient18(ingredientList.get(17));
        meal.setStrIngredient19(ingredientList.get(18));
        meal.setStrIngredient20(ingredientList.get(19));

    }

    public void measureHandler(String str) {
        List<String> measureList = new ArrayList<String>();
        String[] arrOfStr = str.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {
            measureList.add(arrOfStr[i]);
        }
        for (int i = arrOfStr.length; i < 19; i++) {
            measureList.add(null);
        }

        meal.setStrMeasure1(measureList.get(0));
        meal.setStrMeasure2(measureList.get(1));
        meal.setStrMeasure3(measureList.get(2));
        meal.setStrMeasure4(measureList.get(3));
        meal.setStrMeasure5(measureList.get(4));
        meal.setStrMeasure6(measureList.get(5));
        meal.setStrMeasure7(measureList.get(6));
        meal.setStrMeasure8(measureList.get(7));
        meal.setStrMeasure9(measureList.get(8));
        meal.setStrMeasure10(measureList.get(9));
        meal.setStrMeasure11(measureList.get(10));
        meal.setStrMeasure12(measureList.get(11));
        meal.setStrMeasure13(measureList.get(12));
        meal.setStrMeasure14(measureList.get(13));
        meal.setStrMeasure15(measureList.get(14));
        meal.setStrMeasure16(measureList.get(15));
        meal.setStrMeasure17(measureList.get(16));
        meal.setStrMeasure18(measureList.get(17));
        meal.setStrMeasure19(measureList.get(18));
        meal.setStrMeasure20(measureList.get(19));

    }
}