package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FoodSelectionActivity extends AppCompatActivity {

    private DataManagement dataManagement = DataManagement.getInstance();
    private FoodImageAdapter foodToSelectAdapter;
    private FoodImageAdapter selectedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataManagement.createFoods(this);

        String selectedFoodGroup = getIntent().getStringExtra("SELECTED_FOOD_GROUP");
        dataManagement.generateFoodList(selectedFoodGroup);

        foodToSelectAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, dataManagement.getFoodToSelect());
        selectedFoodAdapter = new FoodImageAdapter(this, R.layout.grid_item_layout, dataManagement.getSelectedFood());

        final GridView gridViewFoodToSelect = (GridView) findViewById(R.id.foodToSelect);
        gridViewFoodToSelect.setAdapter(foodToSelectAdapter);

        final GridView gridViewSelectedFood = (GridView) findViewById(R.id.selectedFood);
        gridViewSelectedFood.setAdapter(selectedFoodAdapter);

        gridViewFoodToSelect.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                dataManagement.addSelectedFood(position);
                gridViewFoodToSelect.invalidateViews();
                gridViewFoodToSelect.setAdapter(foodToSelectAdapter);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });


        gridViewSelectedFood.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                dataManagement.removeSelectedFood(position);
                gridViewFoodToSelect.invalidateViews();
                gridViewFoodToSelect.setAdapter(foodToSelectAdapter);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });


    }
}
