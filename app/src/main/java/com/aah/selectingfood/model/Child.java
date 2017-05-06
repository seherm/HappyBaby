package com.aah.selectingfood.model;

import java.util.ArrayList;

/**
 * Created by Manuel on 04.05.2017.
 */

public class Child {
    private int id;
    private String ageGroup;
    private String feedbackFinalGeneral;

    public Child(String ageGroup){
        this.ageGroup = ageGroup;


        if(ageGroup.equals("young")) {
            this.feedbackFinalGeneral = "feedback general young";
        }
        if(ageGroup.equals("middle")) {
            this.feedbackFinalGeneral = "feedback general middle";
        }
        if(ageGroup.equals("old")) {
            this.feedbackFinalGeneral = "feedback general old";
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
     * of the child, different Feedbacks may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackInstant
     */
    public FeedbackInstant giveFeedbackInstantFood(Food food) {
        if(ageGroup.equals("young")) {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantYoungMessage());
        }
        if(ageGroup.equals("middle")) {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantMiddleMessage());
        }
        if(ageGroup.equals("old")) {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantOldMessage());
        }
        return null;
    }

    /*
     * Gives final FeedbackActivity based on a specific food. Depending on the age group
     * of the child, different Feedbacks may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalFood(Food food) {
        if(ageGroup.equals("young")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalYoungMessage(), null);
        }
        if(ageGroup.equals("middle")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalMiddleMessage(), null);
        }
        if(ageGroup.equals("old")) {
            return new FeedbackCard("#FFFFFF", "#000000", food.getFeedbackFinalOldMessage(), null);
        }
        return null;
    }

    /*
     * Gives a summary FeedbackActivity based on a set of food. Depending on the age group
     * of the child, different Feedbacks may be returned.
     *
     * @param   foods    a set of foods the child should give FeedbackActivity about
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalFoodSummary(ArrayList<Food> foods) {
        if(foods==null){
            return new FeedbackCard("#ac8469", "#000000", "You did not select any foods.", null);
        }
        ArrayList<String> selectedFoodGroups = new ArrayList<String>(0);
        for(Food food : foods){
            if(!selectedFoodGroups.contains(food.getName())){
                selectedFoodGroups.add(food.getName());
            }
        }

        if(selectedFoodGroups.size()>=4) {
            return new FeedbackCard("#a2bd87", "#000000", "[summary: good choice]", null);
        } else {
            return new FeedbackCard("#ac8469", "#000000", "[summary: bad choice]", null);
        }
    }


    /*
     * Gives a general FeedbackActivity about the child and its needs. Depending on the age group
     * of the child, different Feedbacks may be returned.
     *
     * @return          the resulting FeedbackCard
     */
    public FeedbackCard giveFeedbackFinalGeneral() {
        return new FeedbackCard("#a2bd87", "#000000", feedbackFinalGeneral, null);
    }

}
