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
            feedbackFinalGeneralStringResourceId = R.string.feedback_general_young;
        }
        if (ageGroup.equals("middle")) {
            feedbackFinalGeneralStringResourceId = R.string.feedback_general_middle;
        }
        if (ageGroup.equals("old")) {
            feedbackFinalGeneralStringResourceId = R.string.feedback_general_old;
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
     * Gives instant Feedback based on a specific food.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackInstant
     */
    public FeedbackInstant giveFeedbackInstantFood(Food food) {
        return new FeedbackInstant((food.getInstantFeedback()));
    }

    /*
     * Gives final Feedback based on all selected food.
     *
     * @param   List<food>    all food selected in FoodSelectionActivity
     * @return   List<FeedbackCard> allFinalFoodFeedback Cards to be show in FeedbackActivity
     */
    public List<FeedbackCard> giveFeedbackFinalFood(List<Food> foods) {

        List<FeedbackCard> finalFeedbackCardsFood = new ArrayList<>();
        boolean vitaminACardAdded = false;
        boolean notSuitableCardAdded = false;

        boolean containsFruit = false;
        boolean containsVegetable = false;
        boolean containsProtein = false;

        for (Food food : foods) {

            if(food.isNotSuitable()){
                notSuitableCardAdded = true;
                //TODO: Add not suitable card
            }

            if(food.isConsideredVitARich()){
                vitaminACardAdded = true;
                //TODO: Add vitamin A card
            }

            if (food.getFoodGroup().equals("Fruit")) {
                containsFruit = true;
            }

            if (food.getFoodGroup().equals("Vegetable")) {
                containsVegetable = true;
            }

            if (food.isConsideredProteinRich()) {
                containsProtein = true;
            }
        }

        if (!containsFruit && !containsVegetable) {
            finalFeedbackCardsFood.add(new FeedbackCard(R.string.feedback_food_lack_of_fruit_title, R.string.feedback_food_lack_of_fruit_message, "lack_fruits_veg.png", false));
        }

        if(!containsProtein){
            finalFeedbackCardsFood.add(new FeedbackCard(R.string.feedback_food_lack_of_protein_present_title, R.string.feedback_food_lack_of_protein_message, "lack_protein.png", false));
        }

        return finalFeedbackCardsFood;
    }


    /*
     * Gives a summary Feedback based on number of different foodgroups of selected food.
     *
     * @param   foods    a set of foods the child should give Feedback about
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalFoodSummary(List<Food> foods) {
        if (foods == null) {
            return new FeedbackCard(R.string.feedback_no_food_selected_title, R.string.feedback_no_food_selected_message, "noFood.png", false);
        }
        List<String> selectedFoodGroups = new ArrayList<>();
        for (Food food : foods) {
            if (!selectedFoodGroups.contains(food.getFoodGroup())) {
                selectedFoodGroups.add(food.getFoodGroup());
            }
        }

        if (selectedFoodGroups.size() >= 4) {
            return new FeedbackCard(R.string.feedback_well_balanced_title, R.string.feedback_well_balanced_message, "balanced.png", true);
        } else {
            if (selectedFoodGroups.size() > 0) {
                return new FeedbackCard(R.string.feedback_not_well_balanced_title, R.string.feedback_not_well_balanced_message, "unbalanced.png", true);
            } else {
                return new FeedbackCard(R.string.feedback_no_food_selected_title, R.string.feedback_no_food_selected_message, "noFood.png", false);
            }
        }
    }


    /*
     * Gives a general Feedback about the child and its needs.
     *
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalGeneral() {
        return new FeedbackCard(R.string.feedback_general_title, feedbackFinalGeneralStringResourceId, "general_take_away.png", false);
    }

}
