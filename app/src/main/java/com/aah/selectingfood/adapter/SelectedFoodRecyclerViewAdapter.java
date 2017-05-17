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

public class SelectedFoodRecyclerViewAdapter extends RecyclerView.Adapter<SelectedFoodRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Food item);
    }

    private List<Food> selectedFood;
    private final OnItemClickListener listener;


    public SelectedFoodRecyclerViewAdapter(List<Food> selectedFood, Context context, OnItemClickListener listener) {
        this.selectedFood = selectedFood;
        Context context1 = context;
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.image);
            textView =(TextView) view.findViewById(R.id.text);
        }

        public void bind(final Food item, final OnItemClickListener listener) {
            textView.setText(item.getName());
            imageView.setImageBitmap(item.getImage());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_food_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(selectedFood.get(position), listener);
        //holder.imageView.setImageBitmap(selectedFood.get(position).getImage());
        //holder.textView.setText(selectedFood.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return selectedFood.size();
    }
}
