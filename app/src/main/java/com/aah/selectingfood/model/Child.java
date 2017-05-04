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
        // TODO
        return new FeedbackInstant("#FFFFFF", "#000000", "text");
    }

    /*
     * Gives final FeedbackActivity based on a specific food. Depending on the age group
     * of the child, different FeedbackActivity may be returned.
     *
     * @param   food    a specific food the child should give FeedbackActivity about
     * @return          the resulting FeedbackActivity
     */
    public FeedbackCard giveFeedbackFinalFood(Food food) {
        // TODO
        return new FeedbackCard("#FFFFFF", "#000000", "title", "text", "image");
    }

    /*
     * Gives a summary FeedbackActivity based on a set of food. Depending on the age group
     * of the child, different FeedbackActivity may be returned.
     *
     * @param   foods    a set of foods the child should give FeedbackActivity about
     * @return          the resulting FeedbackActivity
     */
    public FeedbackCard giveFeedbackFinalFoodSummary(ArrayList<Food> foods) {
        // TODO
        return new FeedbackCard("#FFFFFF", "#000000", "title", "text", "image");
    }


    /*
     * Gives a general FeedbackActivity about the child and its needs. Depending on the age group
     * of the child, different FeedbackActivity may be returned.
     *
     * @return          the resulting FeedbackActivity
     */
    public FeedbackCard giveFeedbackFinalGeneral() {
        // TODO
        return new FeedbackCard("#FFFFFF", "#000000", "title", "text", "image");
    }

}
