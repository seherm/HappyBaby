package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by Manuel on 04.05.2017.
 */

public class Food {
    private int id;
    private String name;
    private String khmerName;
    private String englishName;
    private String foodGroup;
    private Bitmap image;
    private String sound;
    private String borderColor;
    private String backgroundColor;
    private String feedbackInstantYoungColor;
    private String feedbackInstantYoungMessage;
    private String feedbackInstantMiddleColor;
    private String feedbackInstantMiddleMessage;
    private String feedbackInstantOldColor;
    private String feedbackInstantOldMessage;
    private String feedbackFinalYoungColor;
    private String feedbackFinalYoungMessage;
    private String feedbackFinalMiddleColor;
    private String feedbackFinalMiddleMessage;
    private String feedbackFinalOldColor;
    private String feedbackFinalOldMessage;

    public Food(String name, String foodGroup, Bitmap image) {
        this.name = name;
        this.foodGroup = foodGroup;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getFeedbackInstantYoungColor() {
        return feedbackInstantYoungColor;
    }

    public void setFeedbackInstantYoungColor(String feedbackInstantYoungColor) {
        this.feedbackInstantYoungColor = feedbackInstantYoungColor;
    }

    public String getFeedbackInstantYoungMessage() {
        return feedbackInstantYoungMessage;
    }

    public void setFeedbackInstantYoungMessage(String feedbackInstantYoungMessage) {
        this.feedbackInstantYoungMessage = feedbackInstantYoungMessage;
    }

    public String getFeedbackInstantMiddleColor() {
        return feedbackInstantMiddleColor;
    }

    public void setFeedbackInstantMiddleColor(String feedbackInstantMiddleColor) {
        this.feedbackInstantMiddleColor = feedbackInstantMiddleColor;
    }

    public String getFeedbackInstantMiddleMessage() {
        return feedbackInstantMiddleMessage;
    }

    public void setFeedbackInstantMiddleMessage(String feedbackInstantMiddleMessage) {
        this.feedbackInstantMiddleMessage = feedbackInstantMiddleMessage;
    }

    public String getFeedbackInstantOldColor() {
        return feedbackInstantOldColor;
    }

    public void setFeedbackInstantOldColor(String feedbackInstantOldColor) {
        this.feedbackInstantOldColor = feedbackInstantOldColor;
    }

    public String getFeedbackInstantOldMessage() {
        return feedbackInstantOldMessage;
    }

    public void setFeedbackInstantOldMessage(String feedbackInstantOldMessage) {
        this.feedbackInstantOldMessage = feedbackInstantOldMessage;
    }

    public String getFeedbackFinalYoungColor() {
        return feedbackFinalYoungColor;
    }

    public void setFeedbackFinalYoungColor(String feedbackFinalYoungColor) {
        this.feedbackFinalYoungColor = feedbackFinalYoungColor;
    }

    public String getFeedbackFinalYoungMessage() {
        return feedbackFinalYoungMessage;
    }

    public void setFeedbackFinalYoungMessage(String feedbackFinalYoungMessage) {
        this.feedbackFinalYoungMessage = feedbackFinalYoungMessage;
    }

    public String getFeedbackFinalMiddleColor() {
        return feedbackFinalMiddleColor;
    }

    public void setFeedbackFinalMiddleColor(String feedbackFinalMiddleColor) {
        this.feedbackFinalMiddleColor = feedbackFinalMiddleColor;
    }

    public String getFeedbackFinalMiddleMessage() {
        return feedbackFinalMiddleMessage;
    }

    public void setFeedbackFinalMiddleMessage(String feedbackFinalMiddleMessage) {
        this.feedbackFinalMiddleMessage = feedbackFinalMiddleMessage;
    }

    public String getFeedbackFinalOldColor() {
        return feedbackFinalOldColor;
    }

    public void setFeedbackFinalOldColor(String feedbackFinalOldColor) {
        this.feedbackFinalOldColor = feedbackFinalOldColor;
    }

    public String getFeedbackFinalOldMessage() {
        return feedbackFinalOldMessage;
    }

    public void setFeedbackFinalOldMessage(String feedbackFinalOldMessage) {
        this.feedbackFinalOldMessage = feedbackFinalOldMessage;
    }

    public String getKhmerName() {
        return khmerName;
    }

    public void setKhmerName(String khmerName) {
        this.khmerName = khmerName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}



