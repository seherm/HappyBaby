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
        Food apple = new Food("Apple", "Fruit", R.drawable.apple, BitmapFactory.decodeResource(getResources(),R.drawable.apple));
        Food banana = new Food("Banana", "Fruit", R.drawable.banana, BitmapFactory.decodeResource(getResources(),R.drawable.banana));
        Food grapes = new Food("Grapes", "Fruit", R.drawable.grapes,BitmapFactory.decodeResource(getResources(),R.drawable.grapes));
        Food lemon = new Food("Lemon", "Fruit", R.drawable.lemon,BitmapFactory.decodeResource(getResources(),R.drawable.lemon));
        Food mango = new Food("Mango", "Fruit", R.drawable.mango,BitmapFactory.decodeResource(getResources(),R.drawable.mango));
        Food longan = new Food("Longan", "Fruit", R.drawable.longan,BitmapFactory.decodeResource(getResources(),R.drawable.longan));
        Food lycee = new Food("Lycee", "Fruit", R.drawable.lycee,BitmapFactory.decodeResource(getResources(),R.drawable.lycee));

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
