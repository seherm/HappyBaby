package com.aah.selectingfood;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Locale;

/**
 * Created by flori on 08.05.2017.
 */

public class GlobalState extends Application {

    private boolean firstRun = false;

    private static String language = "km";

    private static Context context;

    public void onCreate() {
        super.onCreate();
        GlobalState.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GlobalState.context;
    }

    public boolean getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    public static String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
