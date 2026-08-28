package com.imageforensics.backend.entity;

import jakarta.persistence.*;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "analysis")

public class Analysis {

    //define the parameters.
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String image_name;

    @Column(nullable=false)
    private String prediction;

    @Column(nullable=false)
    private Double confidence;

    @Column(nullable=false)
    private LocalDateTime created_at;


    //creating the default constructor required by JPA and Hibernate.
    public Analysis(){
    }

    //creating the constructors.
    public Analysis(Long id, String image_name, String prediction, Double confidence, LocalDateTime created_at){
        this.id = id;
        this.image_name = image_name;
        this.prediction = prediction;
        this.confidence = confidence;
        this.created_at = created_at;
    }

    //getters and setters.
    public Long getId(){
        return this.id;
    }
    public String getImageName(){
        return this.image_name;
    }
    public String getPrediction(){
        return this.prediction;
    }
    public Double getConfidence(){
        return this.confidence;
    }
    public LocalDateTime getCreatedAt(){
        return this.created_at;
    }

    public void setId(Long id){ 
        this.id = id;
    }
    public void setImageName(String image_name){
        this.image_name = image_name;
    }
    public void setPrediction(String prediction){
        this.prediction = prediction;
    }
    public void setConfidence(Double confidence){
        this.confidence = confidence;
    }
    public void setCreatedAt(LocalDateTime created_at){
        this.created_at = created_at;
    }

}
