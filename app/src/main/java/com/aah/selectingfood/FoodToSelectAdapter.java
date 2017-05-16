package com.aah.selectingfood;

import com.aah.selectingfood.model.*;

import android.content.Context;
import android.provider.ContactsContract;
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

public class FoodToSelectAdapter extends ArrayAdapter implements Filterable {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Food> originalFoods;
    private ArrayList<Food> filteredFoods;

    public FoodToSelectAdapter(Context context, int layoutResourceId, ArrayList<Food> foods) {
        super(context, layoutResourceId, foods);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.filteredFoods = foods;
        this.originalFoods = foods;
    }

    @Override
    public int getCount() {
        return filteredFoods.size();
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

        Food item = filteredFoods.get(position);
        holder.imageTitle.setText(item.getName());
        holder.image.setImageBitmap(item.getImage());

        return row;
    }

    public Food getItemAtPosition(int position) {
        return filteredFoods.get(position);
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            //constraint is the result from text you want to filter against.
            //If there's nothing to filter on, return the original data
            if (constraint == null || constraint.length() == 0) {
                filterResults.values = originalFoods;
                filterResults.count = originalFoods.size();
            } else {
                ArrayList<Food> filterResultsData = new ArrayList<>();
                for (Food item : originalFoods) {
                    //If data matches filter criterion adding it to output array
                    if (item.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterResultsData.add(item);
                    }
                }
                //following two lines are very important as publish result can only take FilterResults foods
                filterResults.values = filterResultsData;
                filterResults.count = filterResultsData.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            filteredFoods = (ArrayList<Food>) results.values;
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
