package com.aah.selectingfood.model;

/**
 * Created by Manuel on 04.05.2017.
 */

public class FeedbackInstant {

    private String text;

    public FeedbackInstant(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
