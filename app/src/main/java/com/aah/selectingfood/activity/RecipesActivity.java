package com.aah.selectingfood.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.DataManagement;

/** This Activity is for showing some suggestions of recipes for good meals**/
public class RecipesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_recipes));

        ImageView imageView = (ImageView) findViewById(R.id.imageViewRecipes);
        imageView.setImageBitmap(DataManagement.getInstance(this).loadBitmapFromAssets("recipe1.png","recipes"));
    }
}
