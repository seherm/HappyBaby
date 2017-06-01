package com.aah.selectingfood.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aah.selectingfood.R;

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
    }
}
