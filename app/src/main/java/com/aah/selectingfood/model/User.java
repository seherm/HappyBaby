package com.aah.selectingfood.model;

import android.content.Context;
import android.graphics.Bitmap;

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
    private Context context;

    public User(Context context){
        this.context = context;
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

    /**
     * The following retrieves the image set for 'happy',
     * or the standard image for 'happy' in bitmap form.
     */
    public Bitmap getImageHappyBitmap() {
        Bitmap imageBitmap = null;
        if(getImageNeutral()!=null){
            imageBitmap = new ImageSaver(context).
                    setFileName(DataManagement.getInstance(context).getUser().getImageHappy()).
                    setDirectoryName("childrenImages").
                    load();
        } else {
            imageBitmap = DataManagement.getInstance(context).loadBitmapFromAssets("childHappyDefault.png", "childrenImages");
        }
        return imageBitmap;
    }

    /**
     * The following retrieves the image set for 'neutral',
     * or the standard image for 'neutral' in bitmap form.
     */
    public Bitmap getImageNeutralBitmap() {
        Bitmap imageBitmap = null;
        if(getImageNeutral()!=null){
            imageBitmap = new ImageSaver(context).
                    setFileName(DataManagement.getInstance(context).getUser().getImageNeutral()).
                    setDirectoryName("childrenImages").
                    load();
        } else {
            imageBitmap = DataManagement.getInstance(context).loadBitmapFromAssets("childNeutralDefault.png", "childrenImages");
        }
        return imageBitmap;
    }

    /**
     * The following retrieves the image set for 'sad',
     * or the standard image for 'sad' in bitmap form.
     */
    public Bitmap getImageSadBitmap() {
        Bitmap imageBitmap = null;
        if(getImageSad()!=null){
            imageBitmap = new ImageSaver(context).
                    setFileName(DataManagement.getInstance(context).getUser().getImageSad()).
                    setDirectoryName("childrenImages").
                    load();
        } else {
            imageBitmap = DataManagement.getInstance(context).loadBitmapFromAssets("childSadDefault.png", "childrenImages");
        }
        return imageBitmap;
    }
}
