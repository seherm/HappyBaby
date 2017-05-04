package com.aah.selectingfood.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Activity;
import android.util.Log;

import com.aah.selectingfood.R;


import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static java.security.AccessController.getContext;

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
    private ArrayList<Food> allFood;

    private DataManagement(Context context) {
        this.context = context;

        // Create user
        user = new User();

        // Create foods
        foodToSelect = new ArrayList<Food>();
        selectedFood = new ArrayList<Food>();
        allFood = new ArrayList<Food>();
        createFoods();
    }

    public static final DataManagement getInstance(Context context) {
        if (instance == null) {
            instance = new DataManagement(context);
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

        // Parse XML
        try {
            InputStream foodsFIS = context.getAssets().open("xml/foods.xml");

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
                    Food tempFood = new Food(eElement.getElementsByTagName("name").item(0).getTextContent(),
                            eElement.getElementsByTagName("foodgroup").item(0).getTextContent(),
                            loadBitmapFromAssets(eElement.getElementsByTagName("image").item(0).getTextContent(), "foodImages"));

                    allFood.add(tempFood);
                }
            }

        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());
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
            } catch (Exception ignored) {
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

    public User getUser() {
        return user;
    }
}

