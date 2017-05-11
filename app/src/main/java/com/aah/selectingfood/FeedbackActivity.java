package com.aah.selectingfood;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.DataManagement;
import com.aah.selectingfood.model.FeedbackCard;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout pagerIndicator;
    private int dotsCount;
    private ImageView[] dots;
    private FeedbackViewPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.pager_introduction);
        pagerIndicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        pagerAdapter = new FeedbackViewPagerAdapter(FeedbackActivity.this, getFeedbackCards());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setPageMargin(100);
        viewPager.addOnPageChangeListener(this);
        setUiPageViewController();
    }

    private List<FeedbackCard> getFeedbackCards(){
        List<FeedbackCard> feedbackCards = new ArrayList<FeedbackCard>();

        // Create all different feedback cards

        // Create summary feedback for children 1-3
        // TODO: Make sure the summary feedback is correct for 1-3 children.
        FeedbackCard finalFoodSummaryFeedback = DataManagement.getInstance(this).getUser().getChildren().get(0).giveFeedbackFinalFoodSummary(DataManagement.getInstance(this).getSelectedFood());
        finalFoodSummaryFeedback.setImage(DataManagement.getInstance(this).loadBitmapFromAssets("can.png","feedbackImages"));
        feedbackCards.add(finalFoodSummaryFeedback);

        // Create individual food feedback for children 1-3
        // TODO: Analyze each food.

        // Create general feedback for children 1-3
        for(Child child : DataManagement.getInstance(this).getUser().getChildren()){
            FeedbackCard generalAgeFeedback = child.giveFeedbackFinalGeneral();
            generalAgeFeedback.setImage(DataManagement.getInstance(this).loadBitmapFromAssets("can.png","feedbackImages"));
            feedbackCards.add(generalAgeFeedback);
        }
        return feedbackCards;
    }


    private void setUiPageViewController() {
        dotsCount = pagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(20, 0, 20, 0);

            pagerIndicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
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
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
