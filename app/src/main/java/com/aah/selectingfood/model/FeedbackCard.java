package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by Manuel on 04.05.2017.
 */

public class FeedbackCard {
    private int id;
    private String backgroundColor;
    private String textColor;
    private String text;
    private Bitmap image;
    private boolean showFoodOnCard;

    public FeedbackCard(String backgroundColor, String textColor, String text, Bitmap image, boolean showFoodOnCard) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.text = text;
        this.image = image;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
