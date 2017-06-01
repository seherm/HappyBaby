package com.aah.selectingfood.model;

/**
 * Created by Manuel on 04.05.2017.
 */

public class FeedbackInstant {
    private int id;
    private String backgroundColor;
    private String textColor;
    private String text;

    public FeedbackInstant(String text){
        this.text = text;
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
}
