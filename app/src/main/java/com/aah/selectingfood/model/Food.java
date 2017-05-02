package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by sebas on 28.04.2017.
 */

public class Food {

    private String mName;
    private String mType;
    private int mImageId;
    private Bitmap image;

    public Food(String mName, String mType, int mImageId, Bitmap image) {
        this.mName = mName;
        this.mType = mType;
        this.mImageId = mImageId;
        this.image = image;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public int getmImageId() {
        return mImageId;
    }

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
