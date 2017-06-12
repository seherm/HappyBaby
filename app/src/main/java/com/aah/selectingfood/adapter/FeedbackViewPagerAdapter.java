package com.aah.selectingfood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.model.FeedbackCard;
import com.aah.selectingfood.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebas on 05.05.2017.
 */

public class FeedbackViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<FeedbackCard> feedbacks;
    private DataManagement dataManagement;
    private SelectedFoodFeedbackRecyclerViewAdapter selectedFoodFeedbackRecyclerViewAdapter;

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

        dataManagement = DataManagement.getInstance(context);
        List<Food> selectedFood = dataManagement.getSelectedFood();

        //Show selected Food on first feedback card
        RecyclerView selectedFoodRecyclerView = (RecyclerView) itemView.findViewById(R.id.selectedFoodFeedbackRecyclerView);
        if (feedbacks.get(position).isShowFoodOnCard() && !selectedFood.isEmpty()) {
            //Configure Selected Food View
            final int MAX_FOOD_CARDS = 12;
            if (selectedFood.size() >= MAX_FOOD_CARDS) {
                List<Food> foodToShowOnCard = new ArrayList<>(selectedFood.subList(0, MAX_FOOD_CARDS - 1));
                Food placeHolder = new Food("...", "empty", null);
                foodToShowOnCard.add(placeHolder);
                selectedFoodFeedbackRecyclerViewAdapter = new SelectedFoodFeedbackRecyclerViewAdapter(foodToShowOnCard);
            } else {
                selectedFoodFeedbackRecyclerViewAdapter = new SelectedFoodFeedbackRecyclerViewAdapter(selectedFood);
            }

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            selectedFoodRecyclerView.setLayoutManager(gridLayoutManager);
            selectedFoodRecyclerView.setAdapter(selectedFoodFeedbackRecyclerViewAdapter);

        } else {
            selectedFoodRecyclerView.setVisibility(View.GONE);
        }

        TextView titleTextView = (TextView) itemView.findViewById(R.id.title_pager_item);
        titleTextView.setText(feedbacks.get(position).getTitleStringResourceId());
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        imageView.setImageBitmap(feedbacks.get(position).getImage());
        TextView textView = (TextView) itemView.findViewById(R.id.text_pager_item);
        textView.setText(feedbacks.get(position).getTextStringResourceId());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

