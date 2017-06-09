package com.aah.selectingfood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aah.selectingfood.R;
import com.aah.selectingfood.adapter.FoodToSelectArrayAdapter;
import com.aah.selectingfood.adapter.SelectedFoodRecyclerViewAdapter;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.model.Food;

/** In this Activity the user selects the food group of the food he wants to select**/
public class FoodGroupSelectionActivity extends BaseActivity {

    private DataManagement dataManagement;
    private SelectedFoodRecyclerViewAdapter selectedFoodRecyclerViewAdapter;
    private GridView foodToSelectGridView;
    private FoodToSelectArrayAdapter foodToSelectArrayAdapter;
    private SearchView searchView;
    private MenuItem item;
    private LinearLayout foodGroup1;
    private LinearLayout foodGroup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_food_group_selection));

        //Configure Selected Food View
        dataManagement = DataManagement.getInstance(this);
        RecyclerView recyclerViewSelectedFood = (RecyclerView) findViewById(R.id.selectedFoodRecyclerView);
        selectedFoodRecyclerViewAdapter = new SelectedFoodRecyclerViewAdapter(dataManagement.getSelectedFood(), getApplication(), new SelectedFoodRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                dataManagement.removeSelectedFood(item);
                selectedFoodRecyclerViewAdapter.notifyDataSetChanged();
                foodToSelectArrayAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(FoodGroupSelectionActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedFood.setLayoutManager(horizontalLayoutManager);
        recyclerViewSelectedFood.setAdapter(selectedFoodRecyclerViewAdapter);

        //Configure Image of the Baby
        ImageView imageViewChild = (ImageView) findViewById(R.id.childImageView);
        Bitmap childImage = dataManagement.getUser().getChildPhotoBitmap();
        imageViewChild.setImageBitmap(childImage);

        //Configure Search View
        foodToSelectArrayAdapter = new FoodToSelectArrayAdapter(this, R.layout.food_to_select_item_layout, dataManagement.getAllFood());
        foodGroup1 = (LinearLayout) findViewById(R.id.foodGroups1);
        foodGroup2 = (LinearLayout) findViewById(R.id.foodGroups2);
        foodToSelectGridView = (GridView) findViewById(R.id.foodToSelect);
        foodToSelectGridView.setAdapter(foodToSelectArrayAdapter);
        foodToSelectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //collapse searchView
                if (searchView.isShown()) {
                    item.collapseActionView();
                    searchView.setQuery("", false);
                }
                Food selectedFood = foodToSelectArrayAdapter.getItemAtPosition(position);
                dataManagement.addSelectedFood(selectedFood);
                foodToSelectArrayAdapter.notifyDataSetChanged();
                selectedFoodRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        selectedFoodRecyclerViewAdapter.notifyDataSetChanged();
        overridePendingTransition(0,0);
    }


    public void goToFoodSelectionPage(View view) {
        switch (view.getId()) {
            case R.id.fruits:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Fruit");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_fruits);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.fruitsBlue);
                break;
            case R.id.legumes:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Legumes");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_legumes);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.legumesBrown);
                break;
            case R.id.herbs:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Herbs");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_herbs);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.herbsGreen);
                break;
            case R.id.meat:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Meat");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_meat);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.meatsRed);
                break;
            case R.id.vegetables:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Vegetable");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_vegetables);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.vegetablesGreen);
                break;
            case R.id.drinks:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Drink");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_drinks);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.junkFoodPink);
                break;
            case R.id.carbohydrates:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Carbohydrate");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_carbohydrates);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.carbohydratesYellow);
                break;
            case R.id.lastUsed:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Last Used");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_ID", R.string.food_group_last_used);
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.lastUsedPurple);
                break;
        }
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void goToFeedbackPage(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public void saveStringToSharedPreferences(String key, String content) {
        SharedPreferences sharedPref = getSharedPreferences("user_selection", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public void saveIntToSharedPreferences(String key, int content) {
        SharedPreferences sharedPref = getSharedPreferences("user_selection", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, content);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //display search bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    foodToSelectGridView.setVisibility(View.GONE);
                    foodGroup1.setVisibility(View.VISIBLE);
                    foodGroup2.setVisibility(View.VISIBLE);
                    return false;
                } else {
                    foodToSelectGridView.setVisibility(View.VISIBLE);
                    foodGroup1.setVisibility(View.GONE);
                    foodGroup2.setVisibility(View.GONE);
                    foodToSelectArrayAdapter.getFilter().filter(newText);
                    return false;
                }
            }
        });

        return true;
    }
}
