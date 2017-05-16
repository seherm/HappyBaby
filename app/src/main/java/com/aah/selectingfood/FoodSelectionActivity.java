package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FoodSelectionActivity extends AppCompatActivity {

    private DataManagement dataManagement;
    private FoodToSelectAdapter foodToSelectAdapter;
    private FoodToSelectAdapter selectedFoodAdapter;
    private SearchView searchView;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);
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

        SharedPreferences sharedPref = getSharedPreferences("user_selection", MODE_PRIVATE);
        final String selectedFoodGroup = sharedPref.getString("SELECTED_FOOD_GROUP",null);
        final int selectedFoodGroupColor = sharedPref.getInt("SELECTED_FOOD_GROUP_COLOR",0);

        dataManagement = DataManagement.getInstance(this);
        dataManagement.generateFoodList(selectedFoodGroup);

        setTitle(selectedFoodGroup);

        foodToSelectAdapter = new FoodToSelectAdapter(this, R.layout.food_to_select_item_layout, dataManagement.getFoodToSelect());
        selectedFoodAdapter = new FoodToSelectAdapter(this, R.layout.food_to_select_item_layout, dataManagement.getSelectedFood());

        final GridView gridViewFoodToSelect = (GridView) findViewById(R.id.foodToSelect);
        gridViewFoodToSelect.setAdapter(foodToSelectAdapter);
        gridViewFoodToSelect.setBackgroundResource(selectedFoodGroupColor);

        final GridView gridViewSelectedFood = (GridView) findViewById(R.id.selectedFood);
        gridViewSelectedFood.setAdapter(selectedFoodAdapter);

        gridViewFoodToSelect.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //collapse searchView
                if (searchView.isShown()){
                    item.collapseActionView();
                    searchView.setQuery("",false);
                }

                Food selectedFood = foodToSelectAdapter.getItemAtPosition(position);
                dataManagement.addSelectedFood(selectedFood);
                foodToSelectAdapter.notifyDataSetChanged();
                selectedFoodAdapter.notifyDataSetChanged();
            }
        });

        gridViewSelectedFood.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Food selectedFood = selectedFoodAdapter.getItemAtPosition(position);
                dataManagement.removeSelectedFood(selectedFood,selectedFoodGroup);
                foodToSelectAdapter.notifyDataSetChanged();
                selectedFoodAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //used to display search bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foodToSelectAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    public void goToFeedbackPage(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }
}

