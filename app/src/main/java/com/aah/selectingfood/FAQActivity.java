package com.aah.selectingfood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast; //todo delete line

import com.facebook.CallbackManager;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO delete following part
        GlobalState state = ((GlobalState) getApplicationContext());
        Context context = getApplicationContext();;
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
    }
}
