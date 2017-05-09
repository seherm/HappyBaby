package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.Arrays;

public class FoodSelectionActivity extends AppCompatActivity {

    private DataManagement dataManagement;
    private FoodImageAdapter foodToSelectAdapter;
    private FoodImageAdapter selectedFoodAdapter;

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

        dataManagement = DataManagement.getInstance(this);
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
                dataManagement.removeSelectedFood(position,selectedFoodGroup);
                gridViewFoodToSelect.invalidateViews();
                gridViewFoodToSelect.setAdapter(foodToSelectAdapter);
                gridViewSelectedFood.invalidateViews();
                gridViewSelectedFood.setAdapter(selectedFoodAdapter);
            }
        });

        //handleIntent(getIntent());
        Log.e("test", Arrays.toString(dataManagement.getFoodToSelect().toArray()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //used to display search bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO apply newtext filter on item list
                Log.e("ActuallyDebug",newText);//TODO delete after debugging
                //foodToSelectAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/

        return true;
    }

    public void goToFeedbackPage(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    /*private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }*/
}

