package com.example.yummyhealth.Model;

public class FeedbackModel {
    public String id;
    public String feedback;
    public Float foodrating;
    public Float apprating;
    public Float riderrating;

    public FeedbackModel() {
    }

    public FeedbackModel(String id, String feedback, Float foodrating, Float apprating, Float riderrating) {
        this.id = id;
        this.feedback = feedback;
        this.foodrating = foodrating;
        this.apprating = apprating;
        this.riderrating = riderrating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setFoodrating(Float foodrating) {
        this.foodrating = foodrating;
    }

    public void setApprating(Float apprating) {
        this.apprating = apprating;
    }

    public void setRiderrating(Float riderrating) {
        this.riderrating = riderrating;
    }
}


