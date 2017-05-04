package com.aah.selectingfood.model;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.aah.selectingfood.R;

import java.util.ArrayList;

/**
 * Created by sebas on 03.05.2017.
 *
 * DataManagement class is a Singleton to store
 * all objects used in different Android Activities.
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
        createFoods();
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
        foodToSelect.clear();
        for (Food food : allFood) {
            if (food.getFoodGroup().equals(foodGroup)) {
                if (!selectedFood.contains(food)) {
                    foodToSelect.add(food);
                }
            }
        }
    }

    public void createFoods() {
        Food apple = new Food("Apple", "Fruits", R.drawable.apple);
        Food banana = new Food("Banana", "Fruits", R.drawable.banana);
        Food grapes = new Food("Grapes", "Fruits", R.drawable.grapes);
        Food lemon = new Food("Lemon", "Fruits", R.drawable.lemon);
        Food mango = new Food("Mango", "Fruits", R.drawable.mango);
        Food longan = new Food("Longan", "Fruits", R.drawable.longan);
        Food lycee = new Food("Lycee", "Fruits", R.drawable.lycee);
        Food test = new Food("Test", "OtherFood", R.drawable.santol);

        allFood.add(apple);
        allFood.add(banana);
        allFood.add(grapes);
        allFood.add(lemon);
        allFood.add(mango);
        allFood.add(longan);
        allFood.add(lycee);
        allFood.add(test);
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

