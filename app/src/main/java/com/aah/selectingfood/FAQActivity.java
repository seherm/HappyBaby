package com.aah.selectingfood;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast; //todo delete line

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO delete following part
        GlobalState state = ((GlobalState) getApplicationContext());
        Context context = getApplicationContext();;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, String.valueOf(state.getFirstRun()), duration);
        toast.show();
    }
}
