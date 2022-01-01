package com.example.asm3;

import static android.content.ContentValues.TAG;
import static android.text.InputType.TYPE_CLASS_NUMBER;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WebView video;
    Meal meal;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    //String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = new User();
        Dish dish = new Dish();

        ImageButton rate = (ImageButton) findViewById(R.id.ratingBarRecipe);
        ImageButton btnSave = (ImageButton) findViewById(R.id.btn_save_recipe);
        ImageButton btnMap = (ImageButton) findViewById(R.id.btn_map_recipe);
        ImageButton btnTimer = (ImageButton) findViewById(R.id.btn_timer_recipe);
        ImageButton btnBack = (ImageButton) findViewById(R.id.backButtonRecipe);
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play_recipe);

        TextView instruction = (TextView) findViewById(R.id.text_instruction);
        TextView nameDish = (TextView) findViewById(R.id.name_recipe);

        ImageView image = (ImageView) findViewById(R.id.image_recipe);

        // imageView should be declared in layout xml file with id `imageView`
//        ImageView imageView = (ImageView) context.findViewById(R.id.imageView);
//        com.squareup.picasso.Picasso.with(context).
//                load(object.get("profile_image")).
//                placeholder(R.mipmap.ic_launcher).
//                into(imageView);

        ListView listIngre = (ListView) findViewById(R.id.list_ingredient_recipe);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Home Button",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("MealID ", "MEAL TO BE SAVED: " + meal.getIdMeal());
//                DocumentReference userDBInfo = db.collection("users").document(userUID);
//                userDBInfo.update("savedMeals", FieldValue.arrayUnion(meal.getIdMeal()));
                Toast.makeText(getApplicationContext(),"Save meals to db",Toast.LENGTH_LONG).show();
            }
        });


//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(image);

        instruction.setText("Preheat the oven to 200C\\/180C Fan\\/Gas 6.\\r\\nPut the biscuits in a large re-sealable freezer bag and bash with a rolling pin into fine crumbs. Melt the butter in a small pan, then add the biscuit crumbs and stir until coated with butter. Tip into the tart tin and, using the back of a spoon, press over the base and sides of the tin to give an even layer. Chill in the fridge while you make the filling.\\r\\nCream together the butter and sugar until light and fluffy. You can do this in a food processor if you have one. Process for 2-3 minutes. Mix in the eggs, then add the ground almonds and almond extract and blend until well combined.\\r\\nPeel the apples, and cut thin slices of apple. Do this at the last minute to prevent the apple going brown. Arrange the slices over the biscuit base. Spread the frangipane filling evenly on top. Level the surface and sprinkle with the flaked almonds.\\r\\nBake for 20-25 minutes until golden-brown and set.\\r\\nRemove from the oven and leave to cool for 15 minutes. Remove the sides of the tin. An easy way to do this is to stand the tin on a can of beans and push down gently on the edges of the tin.\\r\\nTransfer the tart, with the tin base attached, to a serving plate. Serve warm with cream, cr\\u00e8me fraiche or ice cream.\",");


        nameDish.setSelected(true);

        List<String> listIngredient = new ArrayList<>();
        listIngredient.add("20 litre of sucre");
        listIngredient.add("20 litre of sucre1");
        listIngredient.add("20 litre of sucre2");
        listIngredient.add("20 litre of sucre3");
        listIngredient.add("20 litre of sucre4");
        listIngredient.add("20 litre of sucre5");
        listIngredient.add("20 litre of sucre6");
        listIngredient.add("20 litre of sucre7");
        listIngredient.add("20 litre of sucre8");
        listIngredient.add("20 litre of sucre9");

        //ingredients view
        ArrayAdapter adapter = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_checked,
                android.R.id.text1,
                listIngredient);
        listIngre.setAdapter(adapter);

        for(int i=0 ; i< dish.getIngredient().size(); i++ )  {
            listIngre.setItemChecked(i,false);
        }

        listIngre.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listIngre.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " +position);
                CheckedTextView v = (CheckedTextView) view;
            }
        });

        //button timer
        btnTimer.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.create();
            final EditText timer = new EditText (MainActivity.this);
            timer.setHint("Time to countdown");
            timer.setInputType(TYPE_CLASS_NUMBER);
            builder.setView(timer);
            builder.setTitle("SET TIMER:")
                    .setPositiveButton("START", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new CountDownTimer(Integer.parseInt(timer.getText().toString())*1000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    Toast.makeText(MainActivity.this, "seconds remaining: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                                }

                                public void onFinish() {
                                    Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                                }
                            }.start();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null).show();
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.create();
//                final TextView rated = new TextView (MainActivity.this);
//                final TextView voteNum = new TextView (MainActivity.this);
                final RatingBar ratingBar = new RatingBar(MainActivity.this);
                ratingBar.setMax(5);
//                rated.setText("Rate: ");
//                voteNum.setText("Number of Votes: ");
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
//                layout.addView(rated);
//                layout.addView(voteNum);
                layout.addView(ratingBar);
                layout.setPadding(100,100,   100,10);
                builder.setView(layout);
                builder.setTitle("RATE THIS RECIPE")
                        .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                ratingBar.getRating();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null).show();
            }

        });


        btnPlay.setOnClickListener(view ->{
////            String videoID = meal.getVideoURL().substring(meal.getVideoURL().lastIndexOf("=") + 1);
//
//            String videoID = "https://www.youtube.com/watch?v=fITTAIHWFuM";
////            WebView video = new WebView(MainActivity.this);


//            FrameLayout frame = (FrameLayout) findViewById(R.id.frameRecipe);
//            frame.addView(video);
//            video = (WebView) findViewById(R.id.videoRecipe);
//            video.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    return false;
//                }
//            });
//            WebSettings webSettings = video.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setLoadWithOverviewMode(true);
//            webSettings.setUseWideViewPort(true);
//
//            video.loadUrl("https://www.youtube.com/watch?v=fITTAIHWFuM");

//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frameRecipe,new Youtube());
//            fragmentTransaction.commit();
            YouTubePlayerView youTubePlayerView = new YouTubePlayerView(MainActivity.this);
            getLifecycle().addObserver(youTubePlayerView);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "DFMQZjdaXZc";
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.create();
            final LinearLayout layout = new LinearLayout(MainActivity.this);
            layout.addView(youTubePlayerView);
            builder.setView(layout);
            builder.setNegativeButton(android.R.string.ok, null).show();

        });

    }

//    @Override
//    public void onBackPressed() {
//        if(video.canGoBack()){
//            video.goBack();
//        } else
//        super.onBackPressed();
//    }

    public interface firebaseCallback {
        void call(Meal mealList);
    }

}