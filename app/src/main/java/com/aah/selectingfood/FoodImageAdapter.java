package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sebas on 28.04.2017.
 */

public class FoodImageAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Food> foods;

    public FoodImageAdapter(Context context, int layoutResourceId, ArrayList<Food> foods) {
        super(context, layoutResourceId, foods);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.foods = foods;
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

        Food item = foods.get(position);
        holder.imageTitle.setText(item.getName());
        holder.image.setImageResource(item.getImageResourceId());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
