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
import android.widget.Toast;

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

        final GridView gridview1 = (GridView) findViewById(R.id.foodToSelect);
        gridview1.setAdapter(foodToSelectAdapter);

        final GridView gridview2 = (GridView) findViewById(R.id.selectedFood);
        gridview2.setAdapter(selectedFoodAdapter);

        gridview1.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                addSelectedFood(position);
                gridview1.invalidateViews();
                gridview1.setAdapter(foodToSelectAdapter);
                gridview2.invalidateViews();
                gridview2.setAdapter(selectedFoodAdapter);
            }
        });



        gridview2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                removeSelectedFood(position);
                gridview1.invalidateViews();
                gridview1.setAdapter(foodToSelectAdapter);
                gridview2.invalidateViews();
                gridview2.setAdapter(selectedFoodAdapter);
            }
        });



    }


    public void addFoods(String selectedFoodGroup) {
        Food fruit_0 = new Food("Young Coconut", "Fruit", R.drawable.fruit_0, BitmapFactory.decodeResource(getResources(),R.drawable.fruit_0));
        Food fruit_1 = new Food("Banana", "Fruit", R.drawable.fruit_1, BitmapFactory.decodeResource(getResources(),R.drawable.fruit_1));
        Food fruit_2 = new Food("Green Orange", "Fruit", R.drawable.fruit_2,BitmapFactory.decodeResource(getResources(),R.drawable.fruit_2));
        Food fruit_3 = new Food("Coconut", "Fruit", R.drawable.fruit_3,BitmapFactory.decodeResource(getResources(),R.drawable.fruit_3));
        Food fruit_4 = new Food("Mango", "Fruit", R.drawable.fruit_4,BitmapFactory.decodeResource(getResources(),R.drawable.fruit_4));
        Food fruit_5 = new Food("Test", "Fruit", R.drawable.fruit_5,BitmapFactory.decodeResource(getResources(),R.drawable.fruit_5));
        Food fruit_6 = new Food("Test", "Fruit", R.drawable.fruit_6,BitmapFactory.decodeResource(getResources(),R.drawable.fruit_6));

        foodToSelect.add(fruit_0);
        foodToSelect.add(fruit_1);
        foodToSelect.add(fruit_2);
        foodToSelect.add(fruit_3);
        foodToSelect.add(fruit_4);
        foodToSelect.add(fruit_5);
        foodToSelect.add(fruit_6);
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
