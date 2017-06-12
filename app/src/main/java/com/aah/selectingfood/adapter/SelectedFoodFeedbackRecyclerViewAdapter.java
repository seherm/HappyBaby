package com.aah.selectingfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aah.selectingfood.R;
import com.aah.selectingfood.model.Food;

import java.util.List;

/**
 * Created by sebas on 16.05.2017.
 */

public class SelectedFoodFeedbackRecyclerViewAdapter extends RecyclerView.Adapter<SelectedFoodFeedbackRecyclerViewAdapter.ViewHolder> {


    private List<Food> selectedFood;
    private Context context;


    public SelectedFoodFeedbackRecyclerViewAdapter(List<Food> selectedFood) {
        this.selectedFood = selectedFood;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            textView = (TextView) view.findViewById(R.id.text);
        }

        public void bind(final Food item) {
            textView.setText(item.getName());
            imageView.setImageBitmap(item.getImage());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_food_feedback_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(selectedFood.get(position));
    }

    @Override
    public int getItemCount() {
        return selectedFood.size();
    }
}

