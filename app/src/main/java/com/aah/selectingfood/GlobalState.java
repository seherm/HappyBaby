package com.aah.selectingfood;

import android.app.Application;

/**
 * Created by flori on 08.05.2017.
 */

public class GlobalState extends Application {

    private boolean firstRun = false;

    public boolean getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

}