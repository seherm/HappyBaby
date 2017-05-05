package com.aah.selectingfood.model;

import java.util.ArrayList;

/**
 * Created by Manuel on 02.05.2017.
 */

public class User {
    private int id;
    private String imageNeutral;
    private String imageHappy;
    private String imageSad;
    private ArrayList<Child> children = new ArrayList<Child>();

    public User(){
        addChild(new Child("young"));
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

    public void addChild(Child child) {
        this.children.add(child);
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public Boolean hasChildByAgeGroup(String ageGroup){
        for(Child child : children){
            if(child.getAgeGroup().equals(ageGroup)){
                return true;
            }
        }
        return false;
    }

    public void removeChildByAgeGroup(String ageGroup){
        Child childToRemove = null;
        for(Child child : children){
            if(child.getAgeGroup().equals(ageGroup)){
                childToRemove = child;
            }
        }

        if(childToRemove!=null) {
            children.remove(childToRemove);
        }
    }
}
