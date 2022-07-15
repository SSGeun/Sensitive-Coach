package com.example.sensitive_coach.Model;

public class DietMenu {

    private int foodID;         // 음식 ID
    private String foodName;    // 음식 이름
    private double servings;    // 섭취량
    private double calorie;     // 칼로리
    private double protein;
    private double fat;
    private double carbohydrate;
    private String timeAte;     // 먹은 시간

    public DietMenu(int foodID, String foodName, double servings, double calorie, double protein, double fat, double carbohydrate, String timeAte) {

        this.foodID = foodID;
        this.foodName = foodName;
        this.servings = servings;
        this.calorie = calorie;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.timeAte = timeAte;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getTimeAte() {
        return timeAte;
    }

    public void setTimeAte(String timeAte) {
        this.timeAte = timeAte;
    }
}
