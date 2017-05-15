package com.aah.selectingfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aah.selectingfood.model.DataManagement;
import com.aah.selectingfood.model.Food;

public class FoodGroupSelectionActivity extends AppCompatActivity {

    private DataManagement dataManagement;
    private FoodImageAdapter selectedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFeedbackPage(view);
            }
        });

        //Configure Selected Food View
        dataManagement = DataManagement.getInstance(this);
        selectedFoodAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, dataManagement.getSelectedFood());
        final GridView gridViewSelectedFood = (GridView) findViewById(R.id.selectedFood);
        gridViewSelectedFood.setAdapter(selectedFoodAdapter);

        gridViewSelectedFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Food selectedFood = selectedFoodAdapter.getItemAtPosition(position);
                dataManagement.removeSelectedFood(selectedFood);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });

        //Configure Image of the Baby
        ImageView imageViewChild = (ImageView) findViewById(R.id.childImageView);
        Bitmap childDefaultImage = dataManagement.getUser().getImageHappyBitmap();
        imageViewChild.setImageBitmap(childDefaultImage);

        //TODO: Set Instant Feedback Text
        TextView instantFeedback = (TextView) findViewById(R.id.instantFeedback);
    }



    public void goToFoodSelectionPage(View view) {

        switch(view.getId()){
            case R.id.fruits:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP","Fruits");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",R.color.fruitsBlue);
                break;
            case R.id.legumes:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP","Legumes");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",R.color.legumesBrown);
                break;
            case R.id.meat:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP","Meats");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",R.color.meatsRed);
                break;
            case R.id.vegetables:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP","Vegetables");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",R.color.vegetablesGreen);
                break;
            case R.id.junkFood:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP","Junk Food");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",R.color.junkFoodPink);
                break;
            case R.id.starches:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP","Starches");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",R.color.starchesYellow);
                break;
        }
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void goToFeedbackPage(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public void saveStringToSharedPreferences(String key, String content) {
        SharedPreferences sharedPref = getSharedPreferences("user_selection",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public void saveIntToSharedPreferences(String key, int content){
        SharedPreferences sharedPref = getSharedPreferences("user_selection",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, content);
        editor.apply();
    }
}
