package com.aah.selectingfood.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aah.selectingfood.R;

/**
 * This Activity is for showing static content in an frequently asked questions page
 **/
public class FAQActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
