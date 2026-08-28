package com.imageforensics.backend.dto;

public class MlPredictionResponse{

    //declaring variables
    private String prediction;
    private Double confidence;

    //empty constructor
    public MlPredictionResponse(){
    }


    //getters and setters.
    public String getPrediction(){
        return prediction;
    }
    public Double getConfidence(){
        return confidence;
    }
    public void setPrediction(String prediction){
        this.prediction = prediction;
    }
    public void setConfidence(Double confidence){
        this.confidence = confidence;
    }
}