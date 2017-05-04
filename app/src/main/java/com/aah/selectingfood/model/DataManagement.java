package com.aah.selectingfood.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.app.Activity;

import com.aah.selectingfood.R;


import java.io.FileNotFoundException;
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

    private DataManagement(Context context) {
        this.context = context;

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
                    System.out.println("name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("foodgroup : " + eElement.getElementsByTagName("foodgroup").item(0).getTextContent());
                    System.out.println("image : " + eElement.getElementsByTagName("image").item(0).getTextContent());
                    //Food tempFood = new Food(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("foodgroup").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("image").item(0).getTextContent()));
                }
            }

        } catch(Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());
        }


        // TODO: Remove the following and add it to XML parser above
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

