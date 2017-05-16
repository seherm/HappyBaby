package com.aah.selectingfood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aah.selectingfood.R;
import com.aah.selectingfood.adapter.SelectedFoodRecyclerViewAdapter;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.model.Food;

public class FoodGroupSelectionActivity extends AppCompatActivity {

    private DataManagement dataManagement;
    private RecyclerView recyclerViewSelectedFood;
    private SelectedFoodRecyclerViewAdapter selectedFoodRecyclerViewAdapter;

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


        //Configure Selected Food View
        dataManagement = DataManagement.getInstance(this);
        recyclerViewSelectedFood = (RecyclerView) findViewById(R.id.selectedFoodRecyclerView);
        selectedFoodRecyclerViewAdapter = new SelectedFoodRecyclerViewAdapter(dataManagement.getSelectedFood(), getApplication(), new SelectedFoodRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                dataManagement.removeSelectedFood(item);
                selectedFoodRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(FoodGroupSelectionActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedFood.setLayoutManager(horizontalLayoutManager);
        recyclerViewSelectedFood.setAdapter(selectedFoodRecyclerViewAdapter);

        //Configure Image of the Baby
        ImageView imageViewChild = (ImageView) findViewById(R.id.childImageView);
        Bitmap childDefaultImage = dataManagement.getUser().getImageHappyBitmap();
        imageViewChild.setImageBitmap(childDefaultImage);

        //TODO: Set Instant Feedback Text
        TextView instantFeedback = (TextView) findViewById(R.id.instantFeedback);
    }

    public void goToFoodSelectionPage(View view) {
        switch (view.getId()) {
            case R.id.fruits:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Fruits");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.fruitsBlue);
                break;
            case R.id.legumes:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Legumes");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.legumesBrown);
                break;
            case R.id.meat:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Meats");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.meatsRed);
                break;
            case R.id.vegetables:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Vegetables");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.vegetablesGreen);
                break;
            case R.id.junkFood:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Junk Food");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.junkFoodPink);
                break;
            case R.id.starches:
                saveStringToSharedPreferences("SELECTED_FOOD_GROUP", "Starches");
                saveIntToSharedPreferences("SELECTED_FOOD_GROUP_COLOR", R.color.starchesYellow);
                break;
        }
        Intent intent = new Intent(this, FoodSelectionActivity.class);
        startActivity(intent);
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
}
