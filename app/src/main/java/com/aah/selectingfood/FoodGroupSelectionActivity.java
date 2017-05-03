package com.aah.selectingfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.aah.selectingfood.model.DataManagement;

public class FoodGroupSelectionActivity extends AppCompatActivity {

    private String foodGroup;
    private DataManagement dataManagement = DataManagement.getInstance();
    private FoodImageAdapter selectedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedFoodAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, dataManagement.getSelectedFood());
        final GridView gridViewSelectedFood = (GridView) findViewById(R.id.selectedFood);
        gridViewSelectedFood.setAdapter(selectedFoodAdapter);
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
        foodGroup = "Fruit";
        intent.putExtra("SELECTED_FOOD_GROUP", foodGroup);
    }

    public void showOtherFoods(View v) {
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        foodGroup = "Other Food";
        intent.putExtra("SELECTED_FOOD_GROUP", foodGroup);
        startActivity(intent);
    }

}
