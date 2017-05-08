package com.aah.selectingfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aah.selectingfood.model.DataManagement;

public class FoodGroupSelectionActivity extends AppCompatActivity {

    private String foodGroup;
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

        dataManagement = DataManagement.getInstance(this);
        selectedFoodAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, dataManagement.getSelectedFood());
        final GridView gridViewSelectedFood = (GridView) findViewById(R.id.selectedFood);
        gridViewSelectedFood.setAdapter(selectedFoodAdapter);

        gridViewSelectedFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                dataManagement.removeSelectedFood(position);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });
    }

    public void showStarches(View v) {
        foodGroup = "Starches";
        saveToSharedPreferences(foodGroup);
        goToFoodSelectionPage();
    }

    public void showFruits(View v) {
        foodGroup = "Fruits";
        saveToSharedPreferences(foodGroup);
        goToFoodSelectionPage();
    }

    public void showMeats(View v) {
        foodGroup = "Meats";
        saveToSharedPreferences(foodGroup);
        goToFoodSelectionPage();
    }

    public void showLegumes(View v) {
        foodGroup = "Legumes";
        saveToSharedPreferences(foodGroup);
        goToFoodSelectionPage();
    }

    public void showVegetables(View v) {
        foodGroup = "Vegetables";
        saveToSharedPreferences(foodGroup);
        goToFoodSelectionPage();
    }

    public void showJunkFood(View v) {
        foodGroup = "JunkFood";
        saveToSharedPreferences(foodGroup);
        goToFoodSelectionPage();
    }

    public void saveToSharedPreferences(String content) {
        SharedPreferences sharedPref = getSharedPreferences("user_selection",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SELECTED_FOOD_GROUP", content);
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
}
