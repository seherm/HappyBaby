package com.aah.selectingfood.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

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

        // Try to load user from last time
        user = loadUser();
        if (user == null) {
            // No user available, create a new one
            user = new User();
            // Store user right away
            storeUser(user);
        }

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

    public void removeSelectedFood(int position, String selectedFoodGroup) {
        Food selectedItem = selectedFood.get(position);
        if (selectedItem.getFoodGroup().equals(selectedFoodGroup)) {
            foodToSelect.add(selectedFood.get(position));
        }
        selectedFood.remove(position);
    }

    public void removeSelectedFood(int position) {
        selectedFood.remove(position);
    }

    //after selecting foodgroup the foods list will be created
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

    //Generate all foods used in the app
    private void createFoods() {

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

                    // Create food
                    Food tempFood = new Food(eElement.getElementsByTagName("name").item(0).getTextContent(),
                            eElement.getElementsByTagName("foodgroup").item(0).getTextContent(),
                            loadBitmapFromAssets(eElement.getElementsByTagName("image").item(0).getTextContent(), "foodImages"));

                    // Add instant feedback to food
                    Element feedbackNode = (Element) eElement.getElementsByTagName("feedback").item(0);
                    tempFood.setFeedbackInstantYoungMessage(feedbackNode.getElementsByTagName("feedbackInstantYoungMessage").item(0).getTextContent());
                    tempFood.setFeedbackInstantYoungColor(feedbackNode.getElementsByTagName("feedbackInstantYoungColor").item(0).getTextContent());
                    tempFood.setFeedbackInstantMiddleMessage(feedbackNode.getElementsByTagName("feedbackInstantMiddleMessage").item(0).getTextContent());
                    tempFood.setFeedbackInstantMiddleColor(feedbackNode.getElementsByTagName("feedbackInstantMiddleColor").item(0).getTextContent());
                    tempFood.setFeedbackInstantOldMessage(feedbackNode.getElementsByTagName("feedbackInstantOldMessage").item(0).getTextContent());
                    tempFood.setFeedbackInstantOldColor(feedbackNode.getElementsByTagName("feedbackInstantOldColor").item(0).getTextContent());

                    // Add final feedback to food
                    tempFood.setFeedbackFinalYoungMessage(feedbackNode.getElementsByTagName("feedbackFinalYoungMessage").item(0).getTextContent());
                    tempFood.setFeedbackFinalYoungColor(feedbackNode.getElementsByTagName("feedbackFinalYoungColor").item(0).getTextContent());
                    tempFood.setFeedbackFinalMiddleMessage(feedbackNode.getElementsByTagName("feedbackFinalMiddleMessage").item(0).getTextContent());
                    tempFood.setFeedbackFinalMiddleColor(feedbackNode.getElementsByTagName("feedbackFinalMiddleColor").item(0).getTextContent());
                    tempFood.setFeedbackFinalOldMessage(feedbackNode.getElementsByTagName("feedbackFinalOldMessage").item(0).getTextContent());
                    tempFood.setFeedbackFinalOldColor(feedbackNode.getElementsByTagName("feedbackFinalOldColor").item(0).getTextContent());

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
    private User loadUser() {
        SharedPreferences sharedPref = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);
        String imageNeutral = sharedPref.getString("imageNeutral", null);
        String imageHappy = sharedPref.getString("imageHappy", null);
        String imageSad = sharedPref.getString("imageSad", null);
        Boolean hasChildYoung = sharedPref.getBoolean("hasChildYoung", false);
        Boolean hasChildMiddle = sharedPref.getBoolean("hasChildMiddle", false);
        Boolean hasChildOld = sharedPref.getBoolean("hasChildOld", false);

        if (imageNeutral == null && imageHappy == null && imageSad == null && !hasChildYoung && !hasChildMiddle && !hasChildOld) {
            return null;
        } else {
            User user = new User();
            user.setImageNeutral(imageNeutral);
            user.setImageHappy(imageHappy);
            user.setImageSad(imageSad);

            if (hasChildYoung) {
                Child child = new Child("young");
                user.addChild(child);
            } else {
                user.removeChildByAgeGroup("young");
            }
            if (hasChildMiddle) {
                Child child = new Child("middle");
                user.addChild(child);
            } else {
                user.removeChildByAgeGroup("middle");
            }
            if (hasChildOld) {
                Child child = new Child("old");
                user.addChild(child);
            } else {
                user.removeChildByAgeGroup("old");
            }

            return user;
        }
    }

    /*
    * Stores a user and its children in the local shared preferences.
    *
    */
    public void storeUser(User user) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("imageNeutral", user.getImageNeutral());
        editor.putString("imageHappy", user.getImageHappy());
        editor.putString("imageSad", user.getImageSad());
        editor.putBoolean("hasChildYoung", user.hasChildByAgeGroup("young"));
        editor.putBoolean("hasChildMiddle", user.hasChildByAgeGroup("middle"));
        editor.putBoolean("hasChildOld", user.hasChildByAgeGroup("old"));
        editor.commit();
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

