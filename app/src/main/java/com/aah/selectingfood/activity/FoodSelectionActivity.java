package com.aah.selectingfood.activity;

import com.aah.selectingfood.adapter.FoodToSelectArrayAdapter;
import com.aah.selectingfood.R;
import com.aah.selectingfood.adapter.SelectedFoodRecyclerViewAdapter;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.model.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/** In this Activity the user selects the food after selecting the food group in the FoodGroupSelectionActivity before**/
public class FoodSelectionActivity extends AppCompatActivity {

    private DataManagement dataManagement;
    private FoodToSelectArrayAdapter foodToSelectArrayAdapter;
    private SelectedFoodRecyclerViewAdapter selectedFoodRecyclerViewAdapter;
    private SearchView searchView;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFeedbackPage(view);
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("user_selection", MODE_PRIVATE);
        final String selectedFoodGroup = sharedPref.getString("SELECTED_FOOD_GROUP", null);
        final int selectedFoodGroupColor = sharedPref.getInt("SELECTED_FOOD_GROUP_COLOR", 0);
        dataManagement = DataManagement.getInstance(this);
        dataManagement.generateFoodList(selectedFoodGroup);
        setTitle(selectedFoodGroup);

        //Configure Food to Select View

        foodToSelectArrayAdapter = new FoodToSelectArrayAdapter(this, R.layout.food_to_select_item_layout, dataManagement.getFoodToSelect());
        final GridView gridViewFoodToSelect = (GridView) findViewById(R.id.foodToSelect);
        gridViewFoodToSelect.setAdapter(foodToSelectArrayAdapter);
        gridViewFoodToSelect.setBackgroundResource(selectedFoodGroupColor);
        gridViewFoodToSelect.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //collapse searchView
                if (searchView.isShown()) {
                    item.collapseActionView();
                    searchView.setQuery("", false);
                }

                if (v.getId() == R.id.soundButton) {
                    playTest();
                } else {
                    Food selectedFood = foodToSelectArrayAdapter.getItemAtPosition(position);
                    dataManagement.addSelectedFood(selectedFood);
                    dataManagement.storeLastUsedFoodToPrefs();
                    foodToSelectArrayAdapter.notifyDataSetChanged();
                    selectedFoodRecyclerViewAdapter.notifyDataSetChanged();
                    checkForImmediateFeedback(selectedFood);
                }
            }
        });

        //Configure Selected Food View
        dataManagement = DataManagement.getInstance(this);
        RecyclerView recyclerViewSelectedFood = (RecyclerView) findViewById(R.id.selectedFoodRecyclerView);
        selectedFoodRecyclerViewAdapter = new

                SelectedFoodRecyclerViewAdapter(dataManagement.getSelectedFood(), getApplication(), new SelectedFoodRecyclerViewAdapter.OnItemClickListener()

        {
            @Override
            public void onItemClick(Food item) {
                dataManagement.removeSelectedFood(item, selectedFoodGroup);
                selectedFoodRecyclerViewAdapter.notifyDataSetChanged();
                foodToSelectArrayAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(FoodSelectionActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedFood.setLayoutManager(horizontalLayoutManager);
        recyclerViewSelectedFood.setAdapter(selectedFoodRecyclerViewAdapter);

        //Configure Image of the Baby
        ImageView imageViewChild = (ImageView) findViewById(R.id.childImageView);
        Bitmap childImage = dataManagement.getUser().getChildPhotoBitmap();
        imageViewChild.setImageBitmap(childImage);
    }

    public void checkForImmediateFeedback(Food food) {
        TextView instantFeedback = (TextView) findViewById(R.id.instantFeedback);
        List<Child> children = dataManagement.getUser().getChildren();
        for (Child child : children) {
            FeedbackInstant feedbackInstant = child.giveFeedbackInstantFood(food);
            instantFeedback.setText(feedbackInstant.getText());
        }
    }


    public void playTest() {
        MediaPlayer m = new MediaPlayer();
        try {
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = getAssets().openFd("foodSound/fruit_0.m4a");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(false);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //used to display search bar
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
                foodToSelectArrayAdapter.getFilter().filter(newText);
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

