package com.aah.selectingfood.model;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.aah.selectingfood.R;

import java.util.ArrayList;

/**
 * Created by sebas on 03.05.2017.
 */

public class DataManagement {

    private static DataManagement instance;
    private Context context;

    private ArrayList<Food> foodToSelect;
    private ArrayList<Food> selectedFood;
    private ArrayList<Food> allFood;

    private DataManagement() {
        foodToSelect = new ArrayList<Food>();
        selectedFood = new ArrayList<Food>();
        allFood = new ArrayList<Food>();
    }

    public static final DataManagement getInstance() {
        if (instance == null) {
            instance = new DataManagement();
        }
        return instance;
    }

    public void addSelectedFood(int position) {
        selectedFood.add(foodToSelect.get(position));
        foodToSelect.remove(position);
    }

    public void removeSelectedFood(int position) {
        foodToSelect.add(selectedFood.get(position));
        selectedFood.remove(position);
    }

    public void generateFoodList(String foodGroup) {
        for (Food food : allFood) {
            //if (food.getType().equals(foodGroup)) {
                foodToSelect.add(food);
            //}
        }
    }

    public void createFoods(Context context) {
        Food apple = new Food("Apple", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.apple));
        Food banana = new Food("Banana", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.banana));
        Food grapes = new Food("Grapes", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.grapes));
        Food lemon = new Food("Lemon", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.lemon));
        Food mango = new Food("Mango", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.mango));
        Food longan = new Food("Longan", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.longan));
        Food lycee = new Food("Lycee", "Fruit", BitmapFactory.decodeResource(context.getResources(), R.drawable.lycee));

        allFood.add(apple);
        allFood.add(banana);
        allFood.add(grapes);
        allFood.add(lemon);
        allFood.add(mango);
        allFood.add(longan);
        allFood.add(lycee);
    }

    public ArrayList<Food> getFoodToSelect() {
        return foodToSelect;
    }

    public ArrayList<Food> getSelectedFood() {
        return selectedFood;
    }

    public ArrayList<Food> getAllFood() {
        return allFood;
    }
}

