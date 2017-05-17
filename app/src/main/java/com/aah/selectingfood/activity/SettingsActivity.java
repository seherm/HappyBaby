package com.aah.selectingfood.activity;

import com.aah.selectingfood.helper.GlobalState;
import com.aah.selectingfood.helper.MyContextWrapper;
import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.ImageSaver;
import com.aah.selectingfood.model.*;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aah.selectingfood.helper.DataManagement;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkBoxChildYoung;
    private CheckBox checkBoxChildMiddle;
    private CheckBox checkBoxChildOld;
    private ImageButton childrenImageHappy;
    private ImageButton childrenImageNeutral;
    private ImageButton childrenImageSad;
    private DataManagement dataManagement;
    private User user;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int HAPPY_FACE_PHOTO_REQUEST_CODE = 1;
    private static final int NEUTRAL_FACE_PHOTO_REQUEST_CODE = 2;
    private static final int SAD_FACE_PHOTO_REQUEST_CODE = 3;
    private static final int HAPPY_FACE_PHOTO_GALLERY_REQUEST_CODE = 4;
    private static final int NEUTRAL_FACE_PHOTO_GALLERY_REQUEST_CODE = 5;
    private static final int SAD_FACE_PHOTO_GALLERY_REQUEST_CODE = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        checkPermissions();

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

        //check currently selected language
        RadioGroup languageRadioGroup = (RadioGroup) findViewById(R.id.radioGroupLanguage);
        GlobalState state = ((GlobalState) GlobalState.getAppContext());
        String language = state.getLanguage();
        switch (language) {
            case "en":
                languageRadioGroup.check(R.id.radioButtonEnglish);
                break;
            case "km":
                languageRadioGroup.check(R.id.radioButtonKhmer);
                break;
        }
    }

    public void changeLanguage(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonEnglish:
                //if (checked) {
                setLocale("en");
                //}
                break;
            case R.id.radioButtonKhmer:
                //if (checked) {
                setLocale("km");
                //}
                break;
        }
    }

    public void setLocale(String lang) {
        /*Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm); todo delete before completion*/
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString("language", lang).apply();
        GlobalState state = ((GlobalState) getApplicationContext());
        state.setLanguage(lang);

        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
    }

    public void setChildImages() {
        childrenImageHappy.setImageBitmap(dataManagement.getUser().getImageHappyBitmap());
        childrenImageNeutral.setImageBitmap(dataManagement.getUser().getImageNeutralBitmap());
        childrenImageSad.setImageBitmap(dataManagement.getUser().getImageSadBitmap());
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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(SettingsActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            }
        }
    }


    public void selectImage(final View view) {
        try {
            PackageManager packageManager = getPackageManager();
            int hasPerm = packageManager.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                //TODO: Delete Photo
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            takePhoto(view);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            pickPhoto(view);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            } else {
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void takePhoto(View view) {
        int requestCode = 0;

        switch (view.getId()) {
            case R.id.imageButtonPhotoHappy:
                requestCode = HAPPY_FACE_PHOTO_REQUEST_CODE;
                break;
            case R.id.imageButtonPhotoNeutral:
                requestCode = NEUTRAL_FACE_PHOTO_REQUEST_CODE;
                break;
            case R.id.imageButtonPhotoSad:
                requestCode = SAD_FACE_PHOTO_REQUEST_CODE;
                break;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null && requestCode != 0) {
            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    public void pickPhoto(View view){
        int requestCode = 0;

        switch (view.getId()) {
            case R.id.imageButtonPhotoHappy:
                requestCode = HAPPY_FACE_PHOTO_GALLERY_REQUEST_CODE;
                break;
            case R.id.imageButtonPhotoNeutral:
                requestCode = NEUTRAL_FACE_PHOTO_GALLERY_REQUEST_CODE;
                break;
            case R.id.imageButtonPhotoSad:
                requestCode = SAD_FACE_PHOTO_GALLERY_REQUEST_CODE;
                break;
        }
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HAPPY_FACE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
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

        } else if (requestCode == NEUTRAL_FACE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
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

        } else if (requestCode == SAD_FACE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
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

        } else if (requestCode == SAD_FACE_PHOTO_GALLERY_REQUEST_CODE) {
            Uri selectedImage = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                childrenImageSad.setImageBitmap(imageBitmap);
                // Store image
                //TODO: Saving is very slow
                new ImageSaver(this).
                        setFileName("imageSad.png").
                        setDirectoryName("childrenImages").
                        save(imageBitmap);
                user.setImageSad("imageSad.png");
                dataManagement.storeUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase));
    }
}
