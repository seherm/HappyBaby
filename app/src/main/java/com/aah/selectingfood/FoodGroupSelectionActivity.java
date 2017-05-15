package com.aah.selectingfood;

import android.content.Context;
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
import com.aah.selectingfood.model.FoodGroup;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.aah.selectingfood.R.id.imageViewChild;

public class FoodGroupSelectionActivity extends AppCompatActivity {

    private String foodGroup;
    private DataManagement dataManagement;
    private FoodImageAdapter selectedFoodAdapter;
    private FoodGroupAdapter foodGroupAdapter;

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

        //Configure Food Groups View
        foodGroupAdapter = new FoodGroupAdapter(this, R.layout.grid_item_layout, createFoodGroups());
        final GridView gridViewFoodGroup = (GridView) findViewById(R.id.foodGroups);
        gridViewFoodGroup.setAdapter(foodGroupAdapter);

        gridViewFoodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FoodGroup selectedFoodGroup = foodGroupAdapter.getItemAtPosition(position);
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP",selectedFoodGroup.getName());
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR",selectedFoodGroup.getBackgroundColor());
                goToFoodSelectionPage();
            }
        });

        //Configure Image of the Baby
        ImageView imageViewChild = (ImageView) findViewById(R.id.childImageView);
        Bitmap childDefaultImage = dataManagement.getUser().getImageHappyBitmap();
        imageViewChild.setImageBitmap(childDefaultImage);

        //TODO: Set Instant Feedback Text
        TextView instantFeedback = (TextView) findViewById(R.id.instantFeedback);
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

    public void goToFoodSelectionPage() {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void goToFeedbackPage(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public List<FoodGroup> createFoodGroups(){
        List<FoodGroup> foodGroups = new ArrayList<>();
        FoodGroup fruits = new FoodGroup("Fruits",dataManagement.loadBitmapFromAssets("fruits.png","foodGroupImages"),R.color.fruitsBlue);
        foodGroups.add(fruits);
        FoodGroup legumes = new FoodGroup("Legumes",dataManagement.loadBitmapFromAssets("legumes.png","foodGroupImages"),R.color.legumesBrown);
        foodGroups.add(legumes);
        FoodGroup meat = new FoodGroup("Meats",dataManagement.loadBitmapFromAssets("meat.png","foodGroupImages"),R.color.meatsRed);
        foodGroups.add(meat);
        FoodGroup vegetables = new FoodGroup("Vegetables",dataManagement.loadBitmapFromAssets("vegetables.png","foodGroupImages"),R.color.vegetablesGreen);
        foodGroups.add(vegetables);
        FoodGroup junkFood = new FoodGroup("Junk Food",dataManagement.loadBitmapFromAssets("junk_food.png","foodGroupImages"),R.color.junkFoodPink);
        foodGroups.add(junkFood);
        FoodGroup starches = new FoodGroup("Starches",dataManagement.loadBitmapFromAssets("starches.png","foodGroupImages"),R.color.starchesYellow);
        foodGroups.add(starches);
        return foodGroups;
    }
}
