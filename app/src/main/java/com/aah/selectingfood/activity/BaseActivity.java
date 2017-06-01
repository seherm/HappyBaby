package com.aah.selectingfood.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.aah.selectingfood.helper.LocaleHelper;

/**
 * Created by sebas on 01.06.2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
