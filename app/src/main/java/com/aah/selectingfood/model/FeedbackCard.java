package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by Manuel on 04.05.2017.
 */

public class FeedbackCard {
    private int id;
    private String backgroundColor;
    private String textColor;
    private int titleStringResourceId;
    private int textStringResourceId;
    private String imageName;
    private Bitmap image;
    private boolean showFoodOnCard;

    public FeedbackCard (int titleStringResourceId, int textStringResourceId, String imageName, boolean showFoodOnCard) {
        this.titleStringResourceId = titleStringResourceId;
        this.textStringResourceId = textStringResourceId;
        this.imageName = imageName;
        this.showFoodOnCard = showFoodOnCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isShowFoodOnCard() {
        return showFoodOnCard;
    }

    public void setShowFoodOnCard(boolean showFoodOnCard) {
        this.showFoodOnCard = showFoodOnCard;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getTextStringResourceId() {
        return textStringResourceId;
    }

    public void setTextStringResourceId(int textStringResourceId) {
        this.textStringResourceId = textStringResourceId;
    }

    public int getTitleStringResourceId() {
        return titleStringResourceId;
    }

    public void setTitleStringResourceId(int titleStringResourceId) {
        this.titleStringResourceId = titleStringResourceId;
    }
}
