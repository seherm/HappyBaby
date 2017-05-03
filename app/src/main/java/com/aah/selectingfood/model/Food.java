package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by sebas on 28.04.2017.
 */

public class Food {

    private String name;
    private String type;
    private Bitmap image;

    public Food(String name, String type, Bitmap image) {
        this.name = name;
        this.type = type;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
