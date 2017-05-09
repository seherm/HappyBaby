package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sebas on 28.04.2017.
 */

public class FoodImageAdapter extends ArrayAdapter implements Filterable {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Food> foods;
    private ArrayList<Food> originalFoods;

    public FoodImageAdapter(Context context, int layoutResourceId, ArrayList<Food> foods) {
        super(context, layoutResourceId, foods);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.foods = foods;
        this.originalFoods = foods;
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
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Food> tempList=new ArrayList<Food>();
            //constraint is the result from text you want to filter against.
            //foods is your data set you will filter from
            if(constraint != null && foods!=null) {
                int length=originalFoods.size();
                int i=0;
                while(i<length){
                    Food item=originalFoods.get(i);
                    //do whatever you wanna do here
                    //adding result set output array

                    if(item.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(item);
                    }

                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults foods
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            foods = (ArrayList<Food>) results.values;
            if (results.count >= 0) {//todo >=? testttest
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    @Override
    public int getCount() {
        return foods.size();
    }
}
