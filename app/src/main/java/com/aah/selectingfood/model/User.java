package com.aah.selectingfood.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.helper.ImageSaver;

import java.util.ArrayList;

/**
 * Created by Manuel on 02.05.2017.
 */

public class User {

    private int id;
    private String childPhoto;
    private ArrayList<Child> children = new ArrayList<>();
    private Context context;

    public User(Context context) {
        this.context = context;
        addChild(new Child("young"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChildPhoto() {
        return childPhoto;
    }

    public void setChildPhoto(String childPhoto) {
        this.childPhoto = childPhoto;
    }

    public void addChild(Child child) {
        this.children.add(child);
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public Boolean hasChildByAgeGroup(String ageGroup) {
        for (Child child : children) {
            if (child.getAgeGroup().equals(ageGroup)) {
                return true;
            }
        }
        return false;
    }

    public void removeChildByAgeGroup(String ageGroup) {
        Child childToRemove = null;
        for (Child child : children) {
            if (child.getAgeGroup().equals(ageGroup)) {
                childToRemove = child;
            }
        }

        if (childToRemove != null) {
            children.remove(childToRemove);
        }
    }

    public Bitmap getChildPhotoBitmap() {
        Bitmap imageBitmap;
        if (getChildPhoto() != null) {
            imageBitmap = new ImageSaver(context).
                    setFileName(DataManagement.getInstance(context).getUser().getChildPhoto()).
                    setDirectoryName("childrenImages").
                    load();
            return imageBitmap;
        } else {
            imageBitmap = DataManagement.getInstance(context).loadBitmapFromAssets("child_happy_default.pngng", "childrenImages");
            return imageBitmap;
        }
    }
}
