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

import java.util.List;

/**
 * Created by sebas on 05.05.2017.
 */

public class FeedbackViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<FeedbackCard> feedbacks;
    private DataManagement dataManagement;
    private SelectedFoodRecyclerViewAdapter selectedFoodRecyclerViewAdapter;

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
        //itemView.setBackgroundResource(R.color.white);
        //itemView.setBackgroundColor(Color.parseColor(feedbacks.get(position).getBackgroundColor()));

        if(feedbacks.get(position).isShowFoodOnCard()){
            //Configure Selected Food View
            RecyclerView selectedFoodRecyclerView = (RecyclerView) itemView.findViewById(R.id.selectedFoodFeedbackRecyclerView);
            dataManagement = DataManagement.getInstance(context);
            selectedFoodRecyclerViewAdapter = new SelectedFoodRecyclerViewAdapter(dataManagement.getSelectedFood(), context, new SelectedFoodRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Food item) {
                    //TODO: create other adapter where no click event listener necessary
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            selectedFoodRecyclerView.setLayoutManager(gridLayoutManager);
            selectedFoodRecyclerView.setAdapter(selectedFoodRecyclerViewAdapter);
        }

        TextView titleTextView = (TextView) itemView.findViewById(R.id.title_pager_item);
        titleTextView.setText("Test Title");
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        imageView.setImageBitmap(feedbacks.get(position).getImage());
        TextView textView = (TextView) itemView.findViewById(R.id.text_pager_item);
        textView.setText(feedbacks.get(position).getText());
        //textView.setTextColor(Color.parseColor(feedbacks.get(position).getTextColor()));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

