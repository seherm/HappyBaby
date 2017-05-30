package com.aah.selectingfood.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast; //todo delete line

import com.aah.selectingfood.helper.ImageSaver;
import com.facebook.CallbackManager;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.aah.selectingfood.helper.GlobalState;
import com.aah.selectingfood.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** This Activity is for showing static content in an frequently asked questions page**/
public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //TODO delete following part
        GlobalState state = ((GlobalState) getApplicationContext());
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, String.valueOf(state.getFirstRun()), duration);
        toast.show();

        /*Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.messenger_button_blue_bg_round);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        ShareDialog.show(this,content);

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);*/

        /*if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();
            shareDialog.show(linkContent);
        }*/


        /*if (ShareDialog.canShow(SharePhotoContent.class)) {
            Log.e("nope","yepyepyep");
        } else Log.e("nope","nope nope nope");*/
        /*Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.messenger_button_blue_bg_round);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            SharePhotoContent photoContent = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            shareDialog.show(photoContent);
            Log.e("nope","yepyepyep");
        } else Log.e("nope","nope nope nope");*/
        /*Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));*/
        /*File file = new File("android.resource://com.aah.selectingfood/" + R.drawable.foodgroup_fruits);
        MediaScannerConnection.scanFile(
                getApplicationContext(),
                new String[]{file.getAbsolutePath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.v("grokkingandroid",
                                "file " + path + " was scanned seccessfully: " + uri);
                    }
                });*/
        // Store image

        // Here, thisActivity is the current activity
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 311390813;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }

        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.foodgroup_fruits);
        /*File file = new ImageSaver(this).
                setExternal(true).
                setFileName("sharePhoto.jpeg").
                setDirectoryName("childrenImages").
                save(imageBitmap);
        if(file.exists()){
            Log.e("testest","yep");
        } else Log.e("testest","nope");

        Uri uriToImage = Uri.fromFile(file);
        Log.e("abc",uriToImage.toString());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));*/

        String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap,"test", null);
        Uri bmpUri = Uri.parse(pathofBmp);
        final Intent emailIntent1 = new Intent(     android.content.Intent.ACTION_SEND);
        emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent1.putExtra(Intent.EXTRA_STREAM, bmpUri);
        emailIntent1.setType("image/png");
        startActivity(Intent.createChooser(emailIntent1, getResources().getText(R.string.send_to)));
    }

}
