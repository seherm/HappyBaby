package com.aah.selectingfood.model;

import android.graphics.Bitmap;

/**
 * Created by Manuel on 04.05.2017.
 */

public class Food implements Comparable<Food> {

    private String name;
    private String khmerName;
    private String englishName;
    private String foodGroup;
    private Bitmap image;
    private String sound;
    private boolean notSuitable;
    private boolean consideredProteinRich;
    private boolean consideredVitARich;
    private String instantFeedback;
    private String instantFeedbackEnglish;
    private String instantFeedbackKhmer;

    public Food(String name, String foodGroup, Bitmap image) {
        this.name = name;
        this.foodGroup = foodGroup;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public boolean isNotSuitable() {
        return notSuitable;
    }

    public void setNotSuitable(boolean notSuitable) {
        this.notSuitable = notSuitable;
    }

    public boolean isConsideredProteinRich() {
        return consideredProteinRich;
    }

    public void setConsideredProteinRich(boolean consideredProteinRich) {
        this.consideredProteinRich = consideredProteinRich;
    }

    public boolean isConsideredVitARich() {
        return consideredVitARich;
    }

    public void setConsideredVitARich(boolean consideredVitARich) {
        this.consideredVitARich = consideredVitARich;
    }

    public String getInstantFeedback() {
        return instantFeedback;
    }

    public void setInstantFeedback(String instantFeedback) {
        this.instantFeedback = instantFeedback;
    }

    public String getInstantFeedbackEnglish() {
        return instantFeedbackEnglish;
    }

    public void setInstantFeedbackEnglish(String instantFeedbackEnglish) {
        this.instantFeedbackEnglish = instantFeedbackEnglish;
    }

    public String getInstantFeedbackKhmer() {
        return instantFeedbackKhmer;
    }

    public void setInstantFeedbackKhmer(String instantFeedbackKhmer) {
        this.instantFeedbackKhmer = instantFeedbackKhmer;
    }

    @Override
    public int compareTo(Food food) {
        return this.name.compareTo(food.getName());
    }
}



