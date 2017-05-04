package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.CheckBox;

import com.aah.selectingfood.model.DataManagement;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    CheckBox checkBoxChildYoung;
    CheckBox checkBoxChildMiddle;
    CheckBox checkBoxChildOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkBoxChildYoung = (CheckBox) findViewById(R.id.checkBoxChildYoung);
        checkBoxChildMiddle = (CheckBox) findViewById(R.id.checkBoxChildMiddle);
        checkBoxChildOld = (CheckBox) findViewById(R.id.checkBoxChildOld);

        if(DataManagement.getInstance(this).getUser().hasChildByAgeGroup("young")){
            checkBoxChildYoung.setChecked(true);
        }
        if(DataManagement.getInstance(this).getUser().hasChildByAgeGroup("middle")){
            checkBoxChildMiddle.setChecked(true);
        }
        if(DataManagement.getInstance(this).getUser().hasChildByAgeGroup("old")){
            checkBoxChildOld.setChecked(true);
        }
    }

    public void changeLanguage(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonEnglish:
                if (checked) {
                  setLocale("en");
                }
                    break;
            case R.id.radioButtonKhmer:
                if (checked) {
                    setLocale("km");
                }
                    break;
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
    }

    /*
     * Changes the ages of the children. Possible ages are:
     * Young: 6-8 months
     * Middle: 9-11 months
     * Old: 12-24 months.
     * Multiple ages can be active at the same time.
     */
    public void changeChildrenAges(View view) {
        if(checkBoxChildYoung.isChecked()){
            if(!DataManagement.getInstance(this).getUser().hasChildByAgeGroup("young")){
                Child child = new Child("young", "final general feedback young child");
                DataManagement.getInstance(this).getUser().addChild(child);
            }
        } else {
            DataManagement.getInstance(this).getUser().removeChildByAgeGroup("young");
        }

        if(checkBoxChildMiddle.isChecked()){
            if(!DataManagement.getInstance(this).getUser().hasChildByAgeGroup("middle")){
                Child child = new Child("middle", "final general feedback middle child");
                DataManagement.getInstance(this).getUser().addChild(child);
            }
        } else {
            DataManagement.getInstance(this).getUser().removeChildByAgeGroup("middle");
        }

        if(checkBoxChildOld.isChecked()){
            if(!DataManagement.getInstance(this).getUser().hasChildByAgeGroup("old")){
                Child child = new Child("old", "final general feedback old child");
                DataManagement.getInstance(this).getUser().addChild(child);
            }
        } else {
            DataManagement.getInstance(this).getUser().removeChildByAgeGroup("old");
        }
    }

}
