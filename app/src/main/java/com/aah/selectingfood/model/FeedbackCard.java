package com.aah.selectingfood.model;

/**
 * Created by Manuel on 04.05.2017.
 */

public class FeedbackCard {
    private int id;
    private String backgroundColor;
    private String textColor;
    private String title;
    private String text;
    private String image;

    public FeedbackCard(String backgroundColor, String textColor, String title, String text, String image){
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.title = title;
        this.text = text;
        this.image = image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
