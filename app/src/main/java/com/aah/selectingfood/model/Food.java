package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by sebas on 28.04.2017.
 */

public class Food {

    private String name;
    private String foodGroup;
    private int imageResourceId;

    public Food(String name, String foodGroup, int imageResourceId) {
        this.name = name;
        this.foodGroup = foodGroup;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String type) {
        this.foodGroup = type;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
