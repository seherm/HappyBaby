package com.aah.selectingfood.helper;

import android.app.Application;
import android.content.Context;

/**
 * Created by flori on 08.05.2017.
 */

public class GlobalState extends Application {

    private boolean firstRun = false;

    public void onCreate() {
        super.onCreate();

    }

    //Here you can define the default app language
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "km"));
    }
}
