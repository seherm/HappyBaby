package com.aah.selectingfood.model;

import com.aah.selectingfood.R;

import java.util.ArrayList;

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
        if (ageGroup.equals("young")) {
            return new FeedbackInstant(food.getFeedbackInstantYoungMessage());
        }
        if (ageGroup.equals("middle")) {
            return new FeedbackInstant(food.getFeedbackInstantMiddleMessage());
        }
        if (ageGroup.equals("old")) {
            return new FeedbackInstant(food.getFeedbackInstantOldMessage());
        }
        return null;
    }

    /*
     * Gives final FeedbackActivity based on a specific food. Depending on the age group
     * of the child, different Feedback may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackCard
     */

    public FeedbackCard giveFeedbackFinalFood(Food food) {

        //TODO: calculate feedback on individual food (protein rich,...)
        /**if (ageGroup.equals("young")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalYoungMessage(), null, false);
        }
        if (ageGroup.equals("middle")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalMiddleMessage(), null, false);
        }
        if (ageGroup.equals("old")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalOldMessage(), null, false);
        }*/
        return null;
    }


    /*
     * Gives a summary FeedbackActivity based on a set of food. Depending on the age group
     * of the child, different Feedback may be returned.
     *
     * @param   foods    a set of foods the child should give FeedbackActivity about
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalFoodSummary(ArrayList<Food> foods) {
        if (foods == null) {
            return new FeedbackCard(R.string.no_food_selected_title, R.string.no_food_selected_message, "noFood.png", false);
        }
        ArrayList<String> selectedFoodGroups = new ArrayList<>();
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
