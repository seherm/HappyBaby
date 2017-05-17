package com.aah.selectingfood.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast; //todo delete line

import com.aah.selectingfood.helper.GlobalState;
import com.aah.selectingfood.R;

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
