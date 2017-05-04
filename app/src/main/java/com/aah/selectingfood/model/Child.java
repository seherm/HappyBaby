package com.aah.selectingfood.model;

import java.util.ArrayList;

/**
 * Created by Manuel on 04.05.2017.
 */

public class Child {
    private int id;
    private String ageGroup;
    private String feedbackFinalGeneral;

    public Child(String ageGroup, String feedbackFinalGeneral){
        this.ageGroup = ageGroup;
        this.feedbackFinalGeneral = feedbackFinalGeneral;
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
     * of the child, different FeedbackActivity may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackActivity
     */
    public FeedbackInstant giveFeedbackInstantFood(Food food) {
        if(ageGroup=="young") {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantYoungMessage());
        }
        if(ageGroup=="middle") {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantMiddleMessage());
        }
        if(ageGroup=="old") {
            return new FeedbackInstant("#FFFFFF", "#000000", food.getFeedbackInstantOldMessage());
        }
        return null;
    }

    /*
     * Gives final FeedbackActivity based on a specific food. Depending on the age group
     * of the child, different FeedbackActivity may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackActivity
     */
    public FeedbackCard giveFeedbackFinalFood(Food food) {
        if(ageGroup=="young") {
            return new FeedbackCard("#FFFFFF", "#000000", "title", food.getFeedbackFinalYoungMessage(), "image");
        }
        if(ageGroup=="middle") {
            return new FeedbackCard("#FFFFFF", "#000000", "title", food.getFeedbackFinalMiddleMessage(), "image");
        }
        if(ageGroup=="old") {
            return new FeedbackCard("#FFFFFF", "#000000", "title", food.getFeedbackFinalOldMessage(), "image");
        }
        return null;
    }

    /*
     * Gives a summary FeedbackActivity based on a set of food. Depending on the age group
     * of the child, different FeedbackActivity may be returned.
     *
     * @param   foods    a set of foods the child should give FeedbackActivity about
     * @return          the resulting FeedbackActivity
     */
    public FeedbackCard giveFeedbackFinalFoodSummary(ArrayList<Food> foods) {
        ArrayList<String> selectedFoodGroups = new ArrayList<String>(0);
        for(Food food : foods){
            if(!selectedFoodGroups.contains(food.getName())){
                selectedFoodGroups.add(food.getName());
            }
        }

        if(selectedFoodGroups.size()>=4) {
            return new FeedbackCard("#FFFFFF", "#000000", "summary: good choice", "summary: good choice", "image");
        } else {
            return new FeedbackCard("#FFFFFF", "#000000", "summary: bad choice", "summary: bad choice", "image");
        }
    }


    /*
     * Gives a general FeedbackActivity about the child and its needs. Depending on the age group
     * of the child, different FeedbackActivity may be returned.
     *
     * @return          the resulting FeedbackActivity
     */
    public FeedbackCard giveFeedbackFinalGeneral() {
        return new FeedbackCard("#FFFFFF", "#000000", "title", feedbackFinalGeneral, "image");
    }

}
