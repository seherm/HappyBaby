package com.aah.selectingfood.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.helper.ImageSaver;
import com.aah.selectingfood.helper.LocaleHelper;
import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.Food;
import com.aah.selectingfood.model.User;

/**
 * This Activity is for showing the settings page, where the user can change ages, photo and language
 **/
public class SettingsActivity extends BaseActivity {

    private RadioButton radioButtonKhmer;
    private RadioButton radioButtonEnglish;
    private CheckBox checkBoxChildYoung;
    private CheckBox checkBoxChildMiddle;
    private CheckBox checkBoxChildOld;
    private ImageView childPhoto;
    private DataManagement dataManagement;
    private User user;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int CAMERA_PHOTO_REQUEST_CODE = 1;
    private static final int GALLERY_PHOTO_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_settings));

        checkPermissions();

        dataManagement = DataManagement.getInstance(this);
        user = dataManagement.getUser();

        radioButtonKhmer = (RadioButton) findViewById(R.id.radioButtonKhmer);
        radioButtonEnglish = (RadioButton) findViewById(R.id.radioButtonEnglish);

        checkBoxChildYoung = (CheckBox) findViewById(R.id.checkBoxChildYoung);
        checkBoxChildMiddle = (CheckBox) findViewById(R.id.checkBoxChildMiddle);
        checkBoxChildOld = (CheckBox) findViewById(R.id.checkBoxChildOld);

        childPhoto = (ImageView) findViewById(R.id.childPhoto);


        String language = LocaleHelper.getLanguage(this);
        if (language.equals("en")) {
            radioButtonEnglish.setChecked(true);
        } else {
            radioButtonKhmer.setChecked(true);
        }


        if (user.hasChildByAgeGroup("young")) {
            checkBoxChildYoung.setChecked(true);
        }
        if (user.hasChildByAgeGroup("middle")) {
            checkBoxChildMiddle.setChecked(true);
        }
        if (user.hasChildByAgeGroup("old")) {
            checkBoxChildOld.setChecked(true);
        }

        setChildImage();
    }


    public void changeLanguage(View view) {

        switch (view.getId()) {
            case R.id.radioButtonEnglish:
                updateViews("en");
                break;
            case R.id.radioButtonKhmer:
                updateViews("km");
                break;
        }
    }

    private void updateViews(String languageCode) {
        LocaleHelper.setLocale(this, languageCode);
        setFoodName(languageCode);
        this.recreate();

    }

    public void setFoodName(String languageCode){
        for (Food food : dataManagement.getAllFood()) {
            if (languageCode.equals("km")) {
                food.setName(food.getKhmerName());
            }else{
                food.setName(food.getEnglishName());
            }
        }
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
        dataManagement.storeUserPrefs(user);
    }


    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this,
                    Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(SettingsActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
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

    public void setChildImage() {
        Bitmap childImage = dataManagement.getUser().getChildPhotoBitmap();
        childPhoto.setImageBitmap(childImage);
    }

    public void selectPhoto(View view) {
        checkPermissions();

        try {
            PackageManager packageManager = getPackageManager();
            int hasPerm = packageManager.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.change_photo);
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                });
                if (user.getChildPhoto() == null) {
                    builder.setItems(R.array.select_photo_items_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0:
                                    dialog.dismiss();
                                    takePhoto();
                                    break;
                                case 1:
                                    pickPhoto();
                                    dialog.dismiss();
                            }
                        }
                    });

                    builder.show();
                } else {
                    builder.setItems(R.array.select_photo_items_array_with_delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0:
                                    dialog.dismiss();
                                    takePhoto();
                                    break;
                                case 1:
                                    pickPhoto();
                                    dialog.dismiss();
                                    break;
                                case 2:
                                    deletePhoto();
                                    dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            } else {
                Toast.makeText(this, R.string.camera_permission_error, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.camera_permission_error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, CAMERA_PHOTO_REQUEST_CODE);
        }
    }

    public void pickPhoto() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, GALLERY_PHOTO_REQUEST_CODE);
    }

    public void deletePhoto() {
        user.setChildPhoto(null);
        childPhoto.setImageBitmap(user.getChildPhotoBitmap());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            childPhoto.setImageBitmap(imageBitmap);

            // Store image
            new ImageSaver(this).
                    setFileName("childPhoto.png").
                    setDirectoryName("childrenImages").
                    save(imageBitmap);
            user.setChildPhoto("childPhoto.png");
            dataManagement.storeUserPrefs(user);


        } else if (requestCode == GALLERY_PHOTO_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap preview_bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null, options);
                childPhoto.setImageBitmap(preview_bitmap);

                //Store image
                new ImageSaver(this).
                        setFileName("childPhoto.png").
                        setDirectoryName("childrenImages").
                        save(preview_bitmap);
                user.setChildPhoto("childPhoto.png");
                dataManagement.storeUserPrefs(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
