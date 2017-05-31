package com.aah.selectingfood.model;

import java.util.ArrayList;

/**
 * Created by Manuel on 04.05.2017.
 */

public class Child {
    private int id;
    private String ageGroup;
    private String feedbackFinalGeneral;

    public Child(String ageGroup) {
        this.ageGroup = ageGroup;


        if (ageGroup.equals("young")) {
            this.feedbackFinalGeneral = "At 6 months, feeding only breast milk is not enough anymore. Your child needs to start eating other foods. Encourage him/her to try new foods and new textures little by little.";
        }
        if (ageGroup.equals("middle")) {
            this.feedbackFinalGeneral = "Between 9 and 11 months, your child experiences a transition to grown up food. He/she wants to touch it. Make sure his/her hands are washed before eating.";
        }
        if (ageGroup.equals("old")) {
            this.feedbackFinalGeneral = "At 1 year, your child is starting to walk. He/she needs more energy! He/she can eat the family meals but will need additional snacks every day. He/she wants to do what grown ups do and discover the world!";
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

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getFeedbackFinalGeneral() {
        return feedbackFinalGeneral;
    }

    public void setFeedbackFinalGeneral(String feedbackFinalGeneral) {
        this.feedbackFinalGeneral = feedbackFinalGeneral;
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
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantYoungMessage());
        }
        if (ageGroup.equals("middle")) {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantMiddleMessage());
        }
        if (ageGroup.equals("old")) {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantOldMessage());
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
        if (ageGroup.equals("young")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalYoungMessage(), null, false);
        }
        if (ageGroup.equals("middle")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalMiddleMessage(), null, false);
        }
        if (ageGroup.equals("old")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalOldMessage(), null, false);
        }
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
            return new FeedbackCard("#ac8469", "#000000", "You did not select any foods.", null, false);
        }
        ArrayList<String> selectedFoodGroups = new ArrayList<>(0);
        for (Food food : foods) {
            if (!selectedFoodGroups.contains(food.getName())) {
                selectedFoodGroups.add(food.getName());
            }
        }

        if (selectedFoodGroups.size() >= 4) {
            return new FeedbackCard("#a2bd87", "#000000", "The meal is well balanced! You are doing great!\n\nTry to vary the meal every day and encourage your child to taste new foods.", null, true);
        } else {
            if (selectedFoodGroups.size() > 0) {
                return new FeedbackCard("#ac8469", "#000000", "Your meal is not well balanced.\n\nTry to vary the meal every day and encourage your child to taste new foods.", null, true);
            } else {
                return new FeedbackCard("#ac8469", "#000000", "You did not select any foods.", null, false);
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
        return new FeedbackCard("#a2bd87", "#000000", feedbackFinalGeneral, null, false);
    }

}
