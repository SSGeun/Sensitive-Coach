package com.example.sensitive_coach.Model;

import java.util.Collection;

public class Exercise {

    private String exerciseClassification;
    private String exerciseName;
    private String exerciseVideopath;
    private String numberOfSets;
    private String numberPerSet;


    public Exercise(String exerciseClassification, String exerciseName, String exerciseVideopath, String numberOfSets, String numberPerSet) {

        this.exerciseClassification = exerciseClassification;
        this.exerciseName = exerciseName;
        this.exerciseVideopath = exerciseVideopath;
        this.numberOfSets = numberOfSets;
        this.numberPerSet = numberPerSet;

    }

    public String getExerciseClassification() {
        return exerciseClassification;
    }

    public void setExerciseClassification(String exerciseClassification) {
        this.exerciseClassification = exerciseClassification;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseVideopath() {
        return exerciseVideopath;
    }

    public void setExerciseVideopath(String exerciseVideopath) {
        this.exerciseVideopath = exerciseVideopath;
    }

    public String getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(String numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public String getNumberPerSet() {
        return numberPerSet;
    }

    public void setNumberPerSet(String numberPerSet) {
        this.numberPerSet = numberPerSet;
    }



}
