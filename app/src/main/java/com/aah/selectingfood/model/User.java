package com.aah.selectingfood.model;

/**
 * Created by Manuel on 02.05.2017.
 */

public class User {
    private int id;
    private String imageNeutral;
    private String imageHappy;
    private String imageSad;

    public User(String imageNeutral, String imageHappy, String imageSad){
        this.imageNeutral = imageNeutral;
        this.imageHappy = imageHappy;
        this.imageSad = imageSad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageNeutral() {
        return imageNeutral;
    }

    public void setImageNeutral(String imageNeutral) {
        this.imageNeutral = imageNeutral;
    }

    public String getImageHappy() {
        return imageHappy;
    }

    public void setImageHappy(String imageHappy) {
        this.imageHappy = imageHappy;
    }

    public String getImageSad() {
        return imageSad;
    }

    public void setImageSad(String imageSad) {
        this.imageSad = imageSad;
    }
}
