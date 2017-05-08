package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.CheckBox;

import com.aah.selectingfood.model.DataManagement;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    CheckBox checkBoxChildYoung;
    CheckBox checkBoxChildMiddle;
    CheckBox checkBoxChildOld;
    ImageButton childrenImageHappy;
    ImageButton childrenImageNeutral;
    ImageButton childrenImageSad;
    DataManagement dataManagement;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataManagement = DataManagement.getInstance(this);
        user = dataManagement.getUser();

        checkBoxChildYoung = (CheckBox) findViewById(R.id.checkBoxChildYoung);
        checkBoxChildMiddle = (CheckBox) findViewById(R.id.checkBoxChildMiddle);
        checkBoxChildOld = (CheckBox) findViewById(R.id.checkBoxChildOld);
        if (user.hasChildByAgeGroup("young")) {
            checkBoxChildYoung.setChecked(true);
        }
        if (user.hasChildByAgeGroup("middle")) {
            checkBoxChildMiddle.setChecked(true);
        }
        if (user.hasChildByAgeGroup("old")) {
            checkBoxChildOld.setChecked(true);
        }

        childrenImageHappy = (ImageButton) findViewById(R.id.imageButtonPhotoHappy);
        childrenImageNeutral = (ImageButton) findViewById(R.id.imageButtonPhotoNeutral);
        childrenImageSad = (ImageButton) findViewById(R.id.imageButtonPhotoSad);

        setChildImages();
    }

    public void changeLanguage(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonEnglish:
                if (checked) {
                    setLocale("en");
                }
                break;
            case R.id.radioButtonKhmer:
                if (checked) {
                    setLocale("km");
                }
                break;
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
    }

    public void setChildImages() {
        // Manuel:
        // I think we should use this, so the user knows which button takes a happy, neutral or sad picture
        // Maybe we can add a camera button below it?

        childrenImageHappy.setImageBitmap(dataManagement.getUser().getImageHappyBitmap());
        childrenImageNeutral.setImageBitmap(dataManagement.getUser().getImageNeutralBitmap());
        childrenImageSad.setImageBitmap(dataManagement.getUser().getImageSadBitmap());

        /*
        childrenImageHappy.setImageBitmap(imageBitmap);
        if (user.getImageHappy() != null) {
            Bitmap imageBitmap = new ImageSaver(this).
                    setFileName(DataManagement.getInstance(this).getUser().getImageHappy()).
                    setDirectoryName("childrenImages").
                    load();
            childrenImageHappy.setImageBitmap(imageBitmap);
        }
        if (user.getImageNeutral() != null) {
            Bitmap imageBitmap = new ImageSaver(this).
                    setFileName(DataManagement.getInstance(this).getUser().getImageNeutral()).
                    setDirectoryName("childrenImages").
                    load();
            childrenImageNeutral.setImageBitmap(imageBitmap);
        }
        if (user.getImageSad() != null) {
            Bitmap imageBitmap = new ImageSaver(this).
                    setFileName(DataManagement.getInstance(this).getUser().getImageSad()).
                    setDirectoryName("childrenImages").
                    load();
            childrenImageSad.setImageBitmap(imageBitmap);
        }
        */
    }

    /*
     * Changes the ages of the children. Possible ages are:
     * Young: 6-8 months
     * Middle: 9-11 months
     * Old: 12-24 months.
     * Multiple ages can be active at the same time.
     */
    public void changeChildrenAges(View view) {

        // If no boxes are checked, return right away
        // We do this because at least one child needs to be selected
        if ((!checkBoxChildYoung.isChecked()) && (!checkBoxChildMiddle.isChecked()) && (!checkBoxChildOld.isChecked())) {
            return;
        }

        if (checkBoxChildYoung.isChecked()) {
            if (!user.hasChildByAgeGroup("young")) {
                Child child = new Child("young");
                user.addChild(child);
            }
        } else {
            user.removeChildByAgeGroup("young");
        }

        if (checkBoxChildMiddle.isChecked()) {
            if (!user.hasChildByAgeGroup("middle")) {
                Child child = new Child("middle");
                user.addChild(child);
            }
        } else {
            user.removeChildByAgeGroup("middle");
        }

        if (checkBoxChildOld.isChecked()) {
            if (!user.hasChildByAgeGroup("old")) {
                Child child = new Child("old");
                user.addChild(child);
            }
        } else {
            user.removeChildByAgeGroup("old");
        }

        // Store user and children
        dataManagement.storeUser(user);
    }

    public void takePhotoHappy(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    public void takePhotoNeutral(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 2);
        }
    }

    public void takePhotoSad(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 3);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            childrenImageHappy.setImageBitmap(imageBitmap);

            // Store image
            new ImageSaver(this).
                    setFileName("imageHappy.png").
                    setDirectoryName("childrenImages").
                    save(imageBitmap);
            user.setImageHappy("imageHappy.png");
            dataManagement.storeUser(user);

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            childrenImageNeutral.setImageBitmap(imageBitmap);

            // Store image
            new ImageSaver(this).
                    setFileName("imageNeutral.png").
                    setDirectoryName("childrenImages").
                    save(imageBitmap);
            user.setImageNeutral("imageNeutral.png");
            dataManagement.storeUser(user);

        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            childrenImageSad.setImageBitmap(imageBitmap);

            // Store image
            new ImageSaver(this).
                    setFileName("imageSad.png").
                    setDirectoryName("childrenImages").
                    save(imageBitmap);
            user.setImageSad("imageSad.png");
            dataManagement.storeUser(user);
        }
    }
}
