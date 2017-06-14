package com.aah.selectingfood.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.helper.ImageSaver;
import com.aah.selectingfood.helper.LocaleHelper;
import com.aah.selectingfood.helper.SampleSlide;
import com.aah.selectingfood.model.AgeGroup;
import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.User;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * This Activity is for showing the onboarding process in the first usage of the app
 **/
public class IntroActivity extends AppIntro {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int CAMERA_PHOTO_REQUEST_CODE = 1;
    private static final int GALLERY_PHOTO_REQUEST_CODE = 2;
    private ImageView childPhoto;
    private CheckBox checkBoxChildYoung;
    private CheckBox checkBoxChildMiddle;
    private CheckBox checkBoxChildOld;
    private DataManagement dataManagement;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        dataManagement = DataManagement.getInstance(this);
        user = dataManagement.getUser();

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        String title = getString(R.string.onboarding_intro_title);
        String description = getString(R.string.onboarding_intro_description);
        int image = R.drawable.app_icon_happy_baby;
        int backgroundColor = Color.parseColor(getString(R.color.darkBlue));
        addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        String title2 = getString(R.string.onboarding_intro_title_2);
        String description2 = getString(R.string.onboarding_intro_description_2);
        int image2 = R.drawable.onboarding_foodselection;
        addSlide(AppIntroFragment.newInstance(title2, description2, image2, backgroundColor));

        addSlide(SampleSlide.newInstance(R.layout.onboarding_take_photo_layout));
        askForPermissions(new String[]{Manifest.permission.CAMERA}, 2);

        addSlide(SampleSlide.newInstance(R.layout.onboarding_select_ages_layout));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Show Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        // Enable swiping
        pager.setPagingEnabled(true);

    }

    public void selectPhoto(View view) {
        checkPermissions();
        childPhoto = (ImageView) pager.findViewById(R.id.childPhoto);
        try {
            PackageManager packageManager = getPackageManager();
            int hasPerm = packageManager.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.settings_alert_change_photo);
                builder.setNegativeButton(R.string.settings_alert_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                });
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
                Toast.makeText(this, R.string.error_camera_permission, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_camera_permission, Toast.LENGTH_SHORT).show();
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


    public void changeChildrenAges(View view) {

        checkBoxChildYoung = (CheckBox) pager.findViewById(R.id.checkBoxChildYoung);
        checkBoxChildMiddle = (CheckBox) pager.findViewById(R.id.checkBoxChildMiddle);
        checkBoxChildOld = (CheckBox) pager.findViewById(R.id.checkBoxChildOld);

        // If no boxes are checked, return right away
        // We do this because at least one child needs to be selected
        if ((!checkBoxChildYoung.isChecked()) && (!checkBoxChildMiddle.isChecked()) && (!checkBoxChildOld.isChecked())) {
            return;
        }

        if (checkBoxChildYoung.isChecked()) {
            if (!user.hasChildByAgeGroup(AgeGroup.YOUNG)) {
                Child child = new Child(AgeGroup.YOUNG);
                user.addChild(child);
            }
        } else {
            user.removeChildByAgeGroup(AgeGroup.YOUNG);
        }

        if (checkBoxChildMiddle.isChecked()) {
            if (!user.hasChildByAgeGroup(AgeGroup.MIDDLE)) {
                Child child = new Child(AgeGroup.MIDDLE);
                user.addChild(child);
            }
        } else {
            user.removeChildByAgeGroup(AgeGroup.MIDDLE);
        }

        if (checkBoxChildOld.isChecked()) {
            if (!user.hasChildByAgeGroup(AgeGroup.OLD)) {
                Child child = new Child(AgeGroup.OLD);
                user.addChild(child);
            }
        } else {
            user.removeChildByAgeGroup(AgeGroup.OLD);
        }

        // Store user and children
        dataManagement.storeUserPrefs(user);
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(IntroActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(IntroActivity.this,
                    Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(IntroActivity.this,
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

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}