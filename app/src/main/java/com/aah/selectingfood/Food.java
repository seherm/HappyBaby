package com.aah.selectingfood;

/**
 * Created by sebas on 28.04.2017.
 */

public class Food {

    private String mName;
    private String mType;
    private int mImageId;

    public Food(String mName, String mType, int mImageId) {
        this.mName = mName;
        this.mType = mType;
        this.mImageId = mImageId;
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
}
