package com.aah.selectingfood;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aah.selectingfood.model.FeedbackCard;

import java.util.List;

/**
 * Created by sebas on 05.05.2017.
 */

public class FeedbackViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<FeedbackCard> feedbacks;

    public FeedbackViewPagerAdapter(Context context, List<FeedbackCard> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;
    }

    @Override
    public int getCount() {
        return feedbacks.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.feedback_item_layout, container, false);
        itemView.setBackgroundColor(Color.parseColor(feedbacks.get(position).getBackgroundColor()));
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        imageView.setImageBitmap(feedbacks.get(position).getImage());
        TextView textView = (TextView) itemView.findViewById(R.id.text_pager_item);
        textView.setText(feedbacks.get(position).getText());
        textView.setTextColor(Color.parseColor(feedbacks.get(position).getTextColor()));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

