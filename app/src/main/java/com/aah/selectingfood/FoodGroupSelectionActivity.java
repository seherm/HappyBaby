package com.aah.selectingfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FoodGroupSelectionActivity extends AppCompatActivity {

    private String foodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showLegumes(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void showVegetables(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void showHerbs(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void showCarbohydrates(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void showFruits(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
    }

    public void showOtherFoods(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        foodGroup = "Other Food";
        intent.putExtra("SELECTED_FOOD_GROUP", foodGroup);
        startActivity(intent);
    }

}
