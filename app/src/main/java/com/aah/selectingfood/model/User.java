package com.aah.selectingfood.model;

/**
 * Created by Manuel on 02.05.2017.
 */

public class User {
    private int id;
    private String picture_neutral;
    private String picture_happy;
    private String picture_sad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture_neutral() {
        return picture_neutral;
    }

    public void setPicture_neutral(String picture_neutral) {
        this.picture_neutral = picture_neutral;
    }

    public String getPicture_happy() {
        return picture_happy;
    }

    public void setPicture_happy(String picture_happy) {
        this.picture_happy = picture_happy;
    }

    public String getPicture_sad() {
        return picture_sad;
    }

    public void setPicture_sad(String picture_sad) {
        this.picture_sad = picture_sad;
    }
}
