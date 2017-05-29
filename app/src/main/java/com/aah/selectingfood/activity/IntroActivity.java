package com.aah.selectingfood.activity;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.helper.ImageSaver;
import com.aah.selectingfood.helper.SampleSlide;
import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.User;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    private static final int CAMERA_PHOTO_REQUEST_CODE = 1;
    private static final int GALLERY_PHOTO_REQUEST_CODE = 2;
    private ImageView childPhoto;
    private DataManagement dataManagement;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        dataManagement = DataManagement.getInstance(this);
        user = dataManagement.getUser();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        /*addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fourthFragment);*/

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        String title = getString(R.string.onboarding_intro_title);
        String description = getString(R.string.onboarding_intro_description);
        int image = R.mipmap.ic_launcher;
        int backgroundColor = Color.parseColor(getString(R.color.blue));
        addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        addSlide(SampleSlide.newInstance(R.layout.onboarding_take_photo_layout));
        askForPermissions(new String[]{Manifest.permission.CAMERA}, 1);

        addSlide(SampleSlide.newInstance(R.layout.onboarding_select_ages_layout));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        //setVibrate(true);
        //setVibrateIntensity(30);
    }

    public void selectPhoto(View view) {
        //askForPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        childPhoto = (ImageView) view;
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
            dataManagement.storeUser(user);


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
                dataManagement.storeUser(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addYoungChild(View view) {
        CheckBox checkBox = (CheckBox) view;

        if (checkBox.isChecked()) {
            if (!user.hasChildByAgeGroup("young")) {
                Child child = new Child("young");
                user.addChild(child);
            } else {
                user.removeChildByAgeGroup("young");
            }
        }
        dataManagement.storeUser(user);
    }

    public void addMiddleChild(View view) {
        CheckBox checkBox = (CheckBox) view;

        if (checkBox.isChecked()) {
            if (!user.hasChildByAgeGroup("middle")) {
                Child child = new Child("middle");
                user.addChild(child);
            } else {
                user.removeChildByAgeGroup("middle");
            }
        }
        dataManagement.storeUser(user);
    }

    public void addOldChild(View view) {
        CheckBox checkBox = (CheckBox) view;

        if (checkBox.isChecked()) {
            if (!user.hasChildByAgeGroup("old")) {
                Child child = new Child("old");
                user.addChild(child);
            } else {
                user.removeChildByAgeGroup("old");
            }
        }
        dataManagement.storeUser(user);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
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
}