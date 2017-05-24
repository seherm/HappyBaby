package com.aah.selectingfood.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.aah.selectingfood.model.Child;
import com.aah.selectingfood.model.Food;
import com.aah.selectingfood.model.User;

import java.util.ArrayList;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import static com.aah.selectingfood.R.id.childPhoto;

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
            user = new User(this.context);
            // Store user right away
            storeUser(user);
        }

        // Create foods
        foodToSelect = new ArrayList<>();
        selectedFood = new ArrayList<>();
        allFood = new ArrayList<>();
        createFoods();
    }

    public static final DataManagement getInstance(Context context) {
        if (instance == null) {
            instance = new DataManagement(context);
        }
        return instance;
    }

    public void addSelectedFood(Food food) {
        selectedFood.add(food);
        foodToSelect.remove(food);
    }

    public void removeSelectedFood(Food food, String selectedFoodGroup) {
        if (food.getFoodGroup().equals(selectedFoodGroup)) {
            foodToSelect.add(food);
        }
        selectedFood.remove(food);
    }

    public void removeSelectedFood(Food food) {
        selectedFood.remove(food);
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

                    // Create food
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String foodgroup = eElement.getElementsByTagName("foodgroup").item(0).getTextContent();
                    Bitmap image = loadBitmapFromAssets(eElement.getElementsByTagName("image").item(0).getTextContent(), "foodImages");
                    Food tempFood = new Food(name, foodgroup, image);

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
        String childPhoto = sharedPref.getString("childPhoto", null);
        Boolean hasChildYoung = sharedPref.getBoolean("hasChildYoung", false);
        Boolean hasChildMiddle = sharedPref.getBoolean("hasChildMiddle", false);
        Boolean hasChildOld = sharedPref.getBoolean("hasChildOld", false);

        if (childPhoto == null && !hasChildYoung && !hasChildMiddle && !hasChildOld) {
            return null;
        } else {
            User user = new User(this.context);
            // Add children
            // Also remove them if they do not belong to user (needed for "young" child, as it is automatically added to the user)
            if ((!user.hasChildByAgeGroup("young")) && hasChildYoung) {
                Child child = new Child("young");
                user.addChild(child);
            } else if (user.hasChildByAgeGroup("young") && (!hasChildYoung)) {
                user.removeChildByAgeGroup("young");
            }
            if ((!user.hasChildByAgeGroup("middle")) && hasChildMiddle) {
                Child child = new Child("middle");
                user.addChild(child);
            } else if (user.hasChildByAgeGroup("middle") && (!hasChildMiddle)) {
                user.removeChildByAgeGroup("middle");
            }
            if ((!user.hasChildByAgeGroup("old")) && hasChildOld) {
                Child child = new Child("old");
                user.addChild(child);
            } else if (user.hasChildByAgeGroup("old") && (!hasChildOld)) {
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
        editor.putString("childPhoto", user.getChildPhoto());
        editor.putBoolean("hasChildYoung", user.hasChildByAgeGroup("young"));
        editor.putBoolean("hasChildMiddle", user.hasChildByAgeGroup("middle"));
        editor.putBoolean("hasChildOld", user.hasChildByAgeGroup("old"));
        editor.apply();
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

