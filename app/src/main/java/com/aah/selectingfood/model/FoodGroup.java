package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by sebas on 15.05.2017.
 */

public class FoodGroup {

    private String name;
    private Bitmap image;
    private int backgroundColor;

    public FoodGroup(String name, Bitmap image, int backgroundColor) {
        this.name = name;
        this.image = image;
        this.backgroundColor = backgroundColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
