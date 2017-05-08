package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.aah.selectingfood.model.DataManagement;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DataManagement dataManagement;
    ImageView imageViewChild;

    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dataManagement = DataManagement.getInstance(this);
        imageViewChild = (ImageView) findViewById(R.id.imageViewChild);
        setChildImage();

        prefs = getSharedPreferences("com.aah.selectingfood", MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_porridgeRecipes) {
            Intent intent = new Intent(this, RecipesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_FAQ) {
            Intent intent = new Intent(this, FAQActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        detectFirstRun();
    }

    public void goToSelectionPage(View view) {
        Intent intent = new Intent(this, FoodGroupSelectionActivity.class);
        startActivity(intent);
    }

    public void setChildImage() {
        if (dataManagement.getUser().getImageHappy() != null) {
            Bitmap imageBitmap = new ImageSaver(this).
                    setFileName(DataManagement.getInstance(this).getUser().getImageHappy()).
                    setDirectoryName("childrenImages").
                    load();
            imageViewChild.setImageBitmap(imageBitmap);
        } else if (dataManagement.getUser().getImageNeutral() != null) {
            Bitmap imageBitmap = new ImageSaver(this).
                    setFileName(DataManagement.getInstance(this).getUser().getImageNeutral()).
                    setDirectoryName("childrenImages").
                    load();
            imageViewChild.setImageBitmap(imageBitmap);
        } else if (dataManagement.getUser().getImageSad() != null) {
            Bitmap imageBitmap = new ImageSaver(this).
                    setFileName(DataManagement.getInstance(this).getUser().getImageSad()).
                    setDirectoryName("childrenImages").
                    load();
            imageViewChild.setImageBitmap(imageBitmap);
        } else {
            Bitmap childDefaultImage = dataManagement.loadBitmapFromAssets("childNeutralDefault.png", "childrenImages");
            imageViewChild.setImageBitmap(childDefaultImage);
        }
    }

    /*
     * detects if app was started before and if not, sets boolean "firstRun" in GlobalState to TRUE
     * accessible via GlobalState.getFirstRun
     */
    public void detectFirstRun() {
        GlobalState state = ((GlobalState) getApplicationContext());

        if (!prefs.contains("firstrun")) {
            Log.e("firstrun","FIRST indeed");
            state.setFirstRun(true);
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}
