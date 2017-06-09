package com.aah.selectingfood.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.aah.selectingfood.model.AgeGroup;
import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.Food;
import com.aah.selectingfood.model.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by sebas on 03.05.2017.
 * <p>
 * DataManagement class is a Singleton to store
 * all objects used in different Android Activities.
 */

public class DataManagement {

    private static DataManagement instance;
    private Context context;

    private User user;

    private ArrayList<Food> foodToSelect;
    private ArrayList<Food> selectedFood;
    private ArrayList<Food> lastUsedFood;
    private ArrayList<Food> allFood;

    private static final int MAX_LAST_USED_FOODS = 6;

    private DataManagement(Context context) {
        this.context = context;

        // Try to load user from last time
        user = loadUserPrefs();
        if (user == null) {
            // No user available, create a new one
            user = new User(this.context);
            // Store user right away
            storeUserPrefs(user);
        }

        // Create foods
        foodToSelect = new ArrayList<>();
        selectedFood = new ArrayList<>();
        allFood = new ArrayList<>();
        lastUsedFood = new ArrayList<>();
        createFoods();
        loadLastUsedFoodFromPrefs();
    }

    public static final DataManagement getInstance(Context context) {
        if (instance == null) {
            instance = new DataManagement(context);
        }
        return instance;
    }

    public void addSelectedFood(Food food) {
        if (!lastUsedFood.contains(food)) {
            if (lastUsedFood.size() >= MAX_LAST_USED_FOODS) {
                lastUsedFood.remove(lastUsedFood.size() - 1);
            }
            lastUsedFood.add(0, food);
        }
        selectedFood.add(0, food);
        foodToSelect.remove(food);
    }

    public void removeSelectedFood(Food food, String selectedFoodGroup) {
        if (selectedFoodGroup.equals("Last Used")) {
            selectedFood.remove(food);
            foodToSelect.add(food);
        } else {
            if (food.getFoodGroup().equals(selectedFoodGroup)) {
                foodToSelect.add(food);
            }
            selectedFood.remove(food);
        }
        Collections.sort(foodToSelect);
    }

    public void removeSelectedFood(Food food) {
        selectedFood.remove(food);
    }

    //after selecting foodgroup the foods list will be created
    public void generateFoodList(String foodGroup) {
        foodToSelect.clear();
        if (foodGroup.equals("Last Used")) {
            foodToSelect.addAll(lastUsedFood);
            for(Food food: selectedFood){
                foodToSelect.remove(food);
            }
        } else {
            for (Food food : allFood) {
                if (food.getFoodGroup().equals(foodGroup)) {
                    if (!selectedFood.contains(food)) {
                        foodToSelect.add(food);
                    }
                }
            }
        }
        Collections.sort(foodToSelect);
    }

    //Generate all foods used in the app
    private void createFoods() {

        // Parse XML
        try {
            InputStream foodsFIS = context.getAssets().open("foodList/foods.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // use the factory to create a documentbuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // create a new document from input stream
            Document doc = builder.parse(foodsFIS);

            // normalize
            doc.getDocumentElement().normalize();

            // loop through all foods
            NodeList nList = doc.getElementsByTagName("food");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String languageCode = LocaleHelper.getLanguage(context);
                    // Create food
                    String name;
                    String instantFeedback;
                    if (languageCode.equals("km")) {
                        name = eElement.getElementsByTagName("khmerName").item(0).getTextContent();
                        instantFeedback = eElement.getElementsByTagName("khmerInstantFeedback").item(0).getTextContent();
                    }else{
                        name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        instantFeedback = eElement.getElementsByTagName("instantFeedback").item(0).getTextContent();
                    }
                    String foodgroup = eElement.getElementsByTagName("foodgroup").item(0).getTextContent();
                    Bitmap image = loadBitmapFromAssets(eElement.getElementsByTagName("image").item(0).getTextContent(), "foodImages");
                    Food tempFood = new Food(name, foodgroup, image);

                    tempFood.setEnglishName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    tempFood.setKhmerName(eElement.getElementsByTagName("khmerName").item(0).getTextContent());
                    tempFood.setSound(eElement.getElementsByTagName("sound").item(0).getTextContent());
                    tempFood.setNotSuitable(Boolean.valueOf(eElement.getElementsByTagName("notSuitable").item(0).getTextContent()));
                    tempFood.setConsideredProteinRich(Boolean.valueOf(eElement.getElementsByTagName("consideredIronProteinRich").item(0).getTextContent()));
                    tempFood.setConsideredVitARich(Boolean.valueOf(eElement.getElementsByTagName("consideredVitARich").item(0).getTextContent()));
                    tempFood.setInstantFeedback(instantFeedback);
                    tempFood.setInstantFeedbackEnglish(eElement.getElementsByTagName("instantFeedback").item(0).getTextContent());
                    tempFood.setInstantFeedbackKhmer(eElement.getElementsByTagName("khmerInstantFeedback").item(0).getTextContent());

                    allFood.add(tempFood);
                }
            }

        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());
        }
    }

    /*
    * Tries to load a user from the local shared preferences.
    * If no user data is available, null is returned.
    *
    * @return          the user, or null
    */
    private User loadUserPrefs() {
        SharedPreferences sharedPref = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);
        String childPhoto = sharedPref.getString("childPhoto", null);
        Boolean hasChildYoung = sharedPref.getBoolean("hasChildYoung", false);
        Boolean hasChildMiddle = sharedPref.getBoolean("hasChildMiddle", false);
        Boolean hasChildOld = sharedPref.getBoolean("hasChildOld", false);

        if (childPhoto == null && !hasChildYoung && !hasChildMiddle && !hasChildOld) {
            return null;
        } else {
            User user = new User(this.context);
            user.setChildPhoto(childPhoto);
            // Add children
            // Also remove them if they do not belong to user (needed for "young" child, as it is automatically added to the user)
            if ((!user.hasChildByAgeGroup(AgeGroup.YOUNG)) && hasChildYoung) {
                Child child = new Child(AgeGroup.YOUNG);
                user.addChild(child);
            } else if (user.hasChildByAgeGroup(AgeGroup.YOUNG) && (!hasChildYoung)) {
                user.removeChildByAgeGroup(AgeGroup.YOUNG);
            }
            if ((!user.hasChildByAgeGroup(AgeGroup.MIDDLE)) && hasChildMiddle) {
                Child child = new Child(AgeGroup.MIDDLE);
                user.addChild(child);
            } else if (user.hasChildByAgeGroup(AgeGroup.MIDDLE) && (!hasChildMiddle)) {
                user.removeChildByAgeGroup(AgeGroup.MIDDLE);
            }
            if ((!user.hasChildByAgeGroup(AgeGroup.OLD)) && hasChildOld) {
                Child child = new Child(AgeGroup.OLD);
                user.addChild(child);
            } else if (user.hasChildByAgeGroup(AgeGroup.OLD) && (!hasChildOld)) {
                user.removeChildByAgeGroup(AgeGroup.OLD);
            }
            return user;
        }
    }

    /*
    * Stores a user and its children in the local shared preferences.
    *
    */
    public void storeUserPrefs(User user) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("childPhoto", user.getChildPhoto());
        editor.putBoolean("hasChildYoung", user.hasChildByAgeGroup(AgeGroup.YOUNG));
        editor.putBoolean("hasChildMiddle", user.hasChildByAgeGroup(AgeGroup.MIDDLE));
        editor.putBoolean("hasChildOld", user.hasChildByAgeGroup(AgeGroup.OLD));
        editor.apply();
    }

    public void storeLastUsedFoodToPrefs() {
        SharedPreferences sharedPref = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> set = new HashSet<String>();
        for (Food food : lastUsedFood) {
            set.add(food.getName());
        }
        editor.putStringSet("LastUsedFoods", set);
        editor.apply();
    }

    public void loadLastUsedFoodFromPrefs() {
        SharedPreferences sharedPref = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);
        Set<String> set = sharedPref.getStringSet("LastUsedFoods", new HashSet<String>());
        for (String foodName : set) {
            for (Food food : allFood) {
                if (foodName.equals(food.getName())) {
                    lastUsedFood.add(food);
                }
            }
        }
    }

    public Bitmap loadBitmapFromAssets(String filename, String subFolderName) {
        InputStream stream = null;
        try {
            stream = context.getAssets().open(subFolderName + "/" + filename);
            return BitmapFactory.decodeStream(stream);
        } catch (Exception ignored) {
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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

    public ArrayList<Food> getLastUsedFood() {
        return lastUsedFood;
    }

    public User getUser() {
        return user;
    }
}

