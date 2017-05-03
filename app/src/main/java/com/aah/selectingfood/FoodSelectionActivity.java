package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;

public class FoodSelectionActivity extends AppCompatActivity {

    private ArrayList<Food> foodToSelect = new ArrayList<Food>();
    private ArrayList<Food> selectedFood = new ArrayList<Food>();
    private FoodImageAdapter foodToSelectAdapter;
    private FoodImageAdapter selectedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String selectedFoodGroup = getIntent().getStringExtra("SELECTED_FOOD_GROUP");
        addFoods(selectedFoodGroup);

        foodToSelectAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, foodToSelect);
        selectedFoodAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, selectedFood);

        final GridView gridViewFoodToSelect = (GridView) findViewById(R.id.foodToSelect);
        gridViewFoodToSelect.setAdapter(foodToSelectAdapter);

        final GridView gridViewSelectedFood = (GridView) findViewById(R.id.selectedFood);
        gridViewSelectedFood.setAdapter(selectedFoodAdapter);

        gridViewFoodToSelect.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                addSelectedFood(position);
                gridViewFoodToSelect.invalidateViews();
                gridViewFoodToSelect.setAdapter(foodToSelectAdapter);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });


        gridViewSelectedFood.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                removeSelectedFood(position);
                gridViewFoodToSelect.invalidateViews();
                gridViewFoodToSelect.setAdapter(foodToSelectAdapter);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });


    }


    public void addFoods(String selectedFoodGroup) {
        Food apple = new Food("Apple", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.apple));
        Food banana = new Food("Banana", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.banana));
        Food grapes = new Food("Grapes", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.grapes));
        Food lemon = new Food("Lemon", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.lemon));
        Food mango = new Food("Mango", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.mango));
        Food longan = new Food("Longan", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.longan));
        Food lycee = new Food("Lycee", "Fruit", BitmapFactory.decodeResource(getResources(), R.drawable.lycee));

        foodToSelect.add(apple);
        foodToSelect.add(banana);
        foodToSelect.add(grapes);
        foodToSelect.add(lemon);
        foodToSelect.add(mango);
        foodToSelect.add(longan);
        foodToSelect.add(lycee);
    }

    public void addSelectedFood(int position) {
        selectedFood.add(foodToSelect.get(position));
        foodToSelect.remove(position);
    }

    public void removeSelectedFood(int position) {
        foodToSelect.add(selectedFood.get(position));
        selectedFood.remove(position);
    }
}
