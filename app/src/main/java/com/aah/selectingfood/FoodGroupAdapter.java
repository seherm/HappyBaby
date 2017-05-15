package com.aah.selectingfood;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aah.selectingfood.model.Food;
import com.aah.selectingfood.model.FoodGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebas on 28.04.2017.
 */

public class FoodGroupAdapter extends ArrayAdapter implements Filterable {

    private Context context;
    private int layoutResourceId;
    private List<FoodGroup> foodGroups;


    public FoodGroupAdapter(Context context, int layoutResourceId, List<FoodGroup> foodGroups) {
        super(context, layoutResourceId, foodGroups);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.foodGroups = foodGroups;
    }

    @Override
    public int getCount() {
        return foodGroups.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        FoodGroup item = foodGroups.get(position);
        row.setBackgroundResource(item.getBackgroundColor());
        holder.imageTitle.setText(item.getName());
        holder.imageTitle.setTextColor(Color.WHITE);
        holder.imageTitle.setTextSize(20);
        holder.image.setImageBitmap(item.getImage());

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    public FoodGroup getItemAtPosition(int position) {
        return foodGroups.get(position);
    }
}
