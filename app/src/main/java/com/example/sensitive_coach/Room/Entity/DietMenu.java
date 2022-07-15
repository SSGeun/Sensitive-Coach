package com.example.sensitive_coach.Room.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

// Entity: Room 작업시 데이터베이스 테이블을 설명하는 주석이 달린 클래스
// 식단 Entity
@Entity
public class DietMenu {

    @PrimaryKey(autoGenerate = true)        // autoGenerate: insert 과정에서 dietMenuID 값 자동 증가
    @ColumnInfo(name = "dietMenuID")
    public int dietMenuID;      // 식단 ID

    @NonNull
    @ColumnInfo(name = "foodName")
    public String foodName;      // 음식 이름

    @NonNull
    @ColumnInfo(name = "intakeAmount")
    public double intakeAmount;     // 음식 섭취량

    @NonNull
    @ColumnInfo(name = "totalCalorie")
    public double totalCalorie;     // 음식 칼로리 * 음식 섭취량

    @NonNull
    @ColumnInfo(name = "totalProtein")
    public double totalProtein;     // 총 단백질(g)

    @NonNull
    @ColumnInfo(name = "totalFat")
    public double totalFat;     // 총 지방(g)

    @NonNull
    @ColumnInfo(name = "totalCarbohydrate")
    public double totalCarbohydrate;     // 총 탄수화물(g)

    @ColumnInfo(name = "intakeDate")
    public String intakeDate;     // 식단 섭취 날짜

    @ColumnInfo(name = "intakeTime")
    public String intakeTime;       // 식단 섭취 시간

    public DietMenu(String foodName, double intakeAmount, double totalCalorie, double totalProtein, double totalFat, double totalCarbohydrate, String intakeDate, String intakeTime) {

        this.foodName = foodName;
        this.intakeAmount = intakeAmount;
        this.totalCalorie = totalCalorie;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
        this.totalCarbohydrate = totalCarbohydrate;
        this.intakeDate = intakeDate;
        this.intakeTime = intakeTime;
    }

    public int getDietMenuID() {
        return dietMenuID;
    }

    public void setDietMenuID(int dietMenuID) {
        this.dietMenuID = dietMenuID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getIntakeAmount() {
        return intakeAmount;
    }

    public void setIntakeAmount(double intakeAmount) {
        this.intakeAmount = intakeAmount;
    }

    public double getTotalCalorie() {
        return totalCalorie;
    }

    public void setTotalCalorie(double totalCalorie) {
        this.totalCalorie = totalCalorie;
    }

    public double getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(double totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getIntakeTime() {
        return intakeTime;
    }

    public void setIntakeTime(String intakeTime) {
        this.intakeTime = intakeTime;
    }
}