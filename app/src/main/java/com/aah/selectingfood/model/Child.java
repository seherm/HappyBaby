package com.aah.selectingfood.model;

import com.aah.selectingfood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel on 04.05.2017.
 */

public class Child {
    private int id;
    private String ageGroup;
    private int feedbackFinalGeneralStringResourceId;


    public Child(String ageGroup) {
        this.ageGroup = ageGroup;

        if (ageGroup.equals("young")) {
            feedbackFinalGeneralStringResourceId = R.string.final_feedback_general_young;
        }
        if (ageGroup.equals("middle")) {
            feedbackFinalGeneralStringResourceId = R.string.final_feedback_general_middle;
        }
        if (ageGroup.equals("old")) {
            feedbackFinalGeneralStringResourceId = R.string.final_feedback_general_old;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgeGroup() {
        return ageGroup;
    }


    /*
     * Gives instant FeedbackActivity based on a specific food. Depending on the age group
     * of the child, different Feedback may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackInstant
     */
    public FeedbackInstant giveFeedbackInstantFood(Food food) {
        return new FeedbackInstant((food.getInstantFeedback()));
    }

    /*
     * Gives final FeedbackActivity based on a specific food. Depending on the age group
     * of the child, different Feedback may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackCard
     */
    public List<FeedbackCard> giveFeedbackFinalFood(List<Food> foods) {

        List<FeedbackCard> finalFeedbackCardsFood = new ArrayList<>();
        boolean junkFoodCardAdded = false;
        boolean softDrinkCardAdded = false;
        boolean containsFruit = false;
        boolean containsVegetable = false;

        for (Food food : foods) {
            if (food.getFoodGroup().equals("Junk Food") && !junkFoodCardAdded) {
                finalFeedbackCardsFood.add(new FeedbackCard(R.string.final_feedback_junk_food_title, R.string.final_feedback_junk_food_message, "junkFood.png", false));
                junkFoodCardAdded = true;
            }

            if (food.getEnglishName().equals("Soft drink") && !softDrinkCardAdded) {
                finalFeedbackCardsFood.add(new FeedbackCard(R.string.final_feedback_soft_drink_title, R.string.final_feedback_soft_drink_message, "softDrink.png", false));
                softDrinkCardAdded = true;
            }

            if(food.getFoodGroup().equals("Fruits")){
                containsFruit = true;
            }

            if(food.getFoodGroup().equals("Vegetables")){
                containsVegetable = true;
            }
        }

        if(!containsFruit || !containsVegetable){
            finalFeedbackCardsFood.add(new FeedbackCard(R.string.final_feedback_lack_of_fruit_or_vegetable_title, R.string.final_feedback_lack_of_fruit_or_vegetable_message, "noFruit.png", false));
        }

        return finalFeedbackCardsFood;
    }


    /*
     * Gives a summary FeedbackActivity based on a set of food. Depending on the age group
     * of the child, different Feedback may be returned.
     *
     * @param   foods    a set of foods the child should give FeedbackActivity about
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalFoodSummary(List<Food> foods) {
        if (foods == null) {
            return new FeedbackCard(R.string.no_food_selected_title, R.string.no_food_selected_message, "noFood.png", false);
        }
        List<String> selectedFoodGroups = new ArrayList<>();
        for (Food food : foods) {
            if (!selectedFoodGroups.contains(food.getFoodGroup())) {
                selectedFoodGroups.add(food.getFoodGroup());
            }
        }

        if (selectedFoodGroups.size() >= 4) {
            return new FeedbackCard(R.string.well_balanced_title, R.string.well_balanced_message, "balanced.png", true);
        } else {
            if (selectedFoodGroups.size() > 0) {
                return new FeedbackCard(R.string.not_well_balanced_title, R.string.not_well_balanced_message, "unbalanced.png", true);
            } else {
                return new FeedbackCard(R.string.no_food_selected_title, R.string.no_food_selected_message, "noFood.png", false);
            }
        }
    }


    /*
     * Gives a general FeedbackActivity about the child and its needs. Depending on the age group
     * of the child, different Feedback may be returned.
     *
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalGeneral() {
        return new FeedbackCard(R.string.feedback_general_title, feedbackFinalGeneralStringResourceId, null, false);
    }

}
