package com.aah.selectingfood.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aah.selectingfood.R;
import com.aah.selectingfood.adapter.FeedbackViewPagerAdapter;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.helper.LocaleHelper;
import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.FeedbackCard;
import com.aah.selectingfood.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This Activity is for showing the feedback about the meal/food the user has chosen to feed his child
 **/
public class FeedbackActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private LinearLayout pagerIndicator;
    private int dotsCount;
    private ImageView[] dots;
    private FeedbackViewPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private ImageView buttonLeft;
    private ImageView buttonRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_feedback));

        setPermissions();

        viewPager = (ViewPager) findViewById(R.id.pager_introduction);
        pagerIndicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        pagerAdapter = new FeedbackViewPagerAdapter(FeedbackActivity.this, getFeedbackCards());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setPageMargin(100);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(getFeedbackCards().size());
        setUiPageViewController();

        buttonRight = (ImageView) findViewById(R.id.buttonRight);
        buttonLeft = (ImageView) findViewById(R.id.buttonLeft);
        buttonLeft.setVisibility(View.GONE);
        int itemsCount = pagerAdapter.getCount();
        if (itemsCount == 1) {
            buttonLeft.setVisibility(View.GONE);
            buttonRight.setVisibility(View.GONE);
        }
    }

    public void ShareFeedbackCard(View v) {
        final int position = viewPager.getCurrentItem();
        View view = viewPager.getChildAt(position);
        view = ((ViewGroup) ((ViewGroup) ((ViewGroup) view).getChildAt(0)).getChildAt(0)).getChildAt(0);
        Bitmap image = getBitmapFromView(view);
        shareGeneral(image);
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    private List<FeedbackCard> getFeedbackCards() {
        List<FeedbackCard> feedbackCards = new ArrayList<>();
        DataManagement dataManagement = DataManagement.getInstance(this);
        User user = dataManagement.getUser();
        List<Child> children = user.getChildren();
        Child firstChild = children.get(0);
        String feedbackImagesFolder = "feedbackImages";

        // Create all different feedback cards

        // Create summary feedback for children
        List<FeedbackCard> finalFoodSummaryFeedbackCards = firstChild.giveFeedbackFinalFoodSummary(dataManagement.getSelectedFood());
        for (FeedbackCard card : finalFoodSummaryFeedbackCards) {
            card.setImage(dataManagement.loadBitmapFromAssets(card.getImageName(), feedbackImagesFolder));
            if (card.getTitleStringResourceId() == R.string.feedback_not_well_balanced_title2) {
                if (LocaleHelper.getLanguage(this).equals("km")) {
                    card.setImage(dataManagement.loadBitmapFromAssets("foodgroups_km.jpg", feedbackImagesFolder));
                }
            }
        }
        feedbackCards.addAll(finalFoodSummaryFeedbackCards);

        // Create individual food feedback for children
        List<FeedbackCard> finalFoodFeedbackCards = firstChild.giveFeedbackFinalFood(dataManagement.getSelectedFood());
        for (FeedbackCard card : finalFoodFeedbackCards) {
            card.setImage(dataManagement.loadBitmapFromAssets(card.getImageName(), feedbackImagesFolder));
            if (card.getTitleStringResourceId() == R.string.feedback_lack_of_fruit_title) {
                if (LocaleHelper.getLanguage(this).equals("km")) {
                    card.setImage(dataManagement.loadBitmapFromAssets("healthysnacks_km.jpg", feedbackImagesFolder));
                }
            }
            feedbackCards.add(card);
        }

        // Create general feedback for children
        for (Child child : DataManagement.getInstance(this).getUser().getChildren()) {
            FeedbackCard generalAgeFeedback = child.giveFeedbackFinalGeneral();
            generalAgeFeedback.setImage(dataManagement.loadBitmapFromAssets(generalAgeFeedback.getImageName(), feedbackImagesFolder));
            feedbackCards.add(generalAgeFeedback);
        }
        return feedbackCards;
    }


    private void setUiPageViewController() {
        dotsCount = pagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_nonselecteditem));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(20, 0, 20, 0);

            pagerIndicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.dot_selecteditem));
    }

    private void setPermissions() {
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 311390813;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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
    }

    private void shareGeneral(Bitmap imageBitmap) {
        setPermissions();

        try {
            PackageManager packageManager = getPackageManager();
            int hasPerm = packageManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                String pathOfBitmap = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "sharepicture", null);
                Uri bmpUri = Uri.parse(pathOfBitmap);
                final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.title_activity_sharing_this_tip)));
            } else
                Toast.makeText(this, R.string.error_write_ext_permission, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_write_ext_permission, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_nonselecteditem));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dot_selecteditem));

        buttonLeft.setVisibility(View.VISIBLE);
        buttonRight.setVisibility(View.VISIBLE);

        if (viewPager.getCurrentItem() == pagerAdapter.getCount() - 1) {
            buttonRight.setVisibility(View.GONE);
        }
        if (viewPager.getCurrentItem() == 0) {
            buttonLeft.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restart_menu, menu);
        return true;
    }

    public void swipePage(View view) {
        buttonLeft.setVisibility(View.VISIBLE);
        buttonRight.setVisibility(View.VISIBLE);

        if (view.getId() == R.id.buttonRight) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
        if (view.getId() == R.id.buttonLeft) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
        if (viewPager.getCurrentItem() == pagerAdapter.getCount() - 1) {
            buttonRight.setVisibility(View.GONE);
        }
        if (viewPager.getCurrentItem() == 0) {
            buttonLeft.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.restart) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
