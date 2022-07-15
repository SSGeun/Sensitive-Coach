package com.example.sensitive_coach.Room.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entity: Room 작업시 데이터베이스 테이블을 설명하는 주석이 달린 클래스
// 음식 Entity
@Entity
public class Food {

    @PrimaryKey(autoGenerate = true)        // autoGenerate: insert 과정에서 foodID 값 자동 증가
    @ColumnInfo(name = "foodID")
    public int foodID;      // 음식 ID

    @NonNull
    @ColumnInfo(name = "foodName")
    public String foodName;     // 음식 이름

    @ColumnInfo(name = "foodGroupName")
    public String foodGroupName;        // 식품군명

    @NonNull
    @ColumnInfo(name = "servingSize")
    public double servingSize;      // 1회 제공량

    @NonNull
    @ColumnInfo(name = "servingUnit")
    public String servingUnit;      // 제공량 단위(g, mL)

    @NonNull
    @ColumnInfo(name = "caloriePerAmount")
    public double caloridPerAmount;        // 단위 수량 당 칼로리(kcal)

    @NonNull
    @ColumnInfo(name = "proteinPerAmount")
    public double proteinPerAmount;     // 단위 수량 당 단백질(g)

    @NonNull
    @ColumnInfo(name = "fatPerAmount")
    public double fatPerAmount;     // 단위 수량 당 지방(g)

    @NonNull
    @ColumnInfo(name = "carbohydratePerAmount")
    public double carbohydratePerAmount;     // 단위 수량 당 탄수화물(g)

    public Food(String foodName, String foodGroupName, double servingSize, String servingUnit, double caloridPerAmount, double proteinPerAmount, double fatPerAmount, double carbohydratePerAmount) {

        this.foodName = foodName;
        this.foodGroupName = foodGroupName;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
        this.caloridPerAmount = caloridPerAmount;
        this.proteinPerAmount = proteinPerAmount;
        this.fatPerAmount = fatPerAmount;
        this.carbohydratePerAmount = carbohydratePerAmount;
    }

    public int getFoodID() { return foodID; }

    public void setFoodID(int foodID) { this.foodID = foodID; }

    @NonNull
    public String getFoodName() { return foodName; }

    public void setFoodName(@NonNull String foodName) { this.foodName = foodName; }

    public String getFoodGroupName() { return foodGroupName; }

    public void setFoodGroupName(String foodGroupName) { this.foodGroupName = foodGroupName; }

    public double getServingSize() { return servingSize; }

    public void setServingSize(double servingSize) { this.servingSize = servingSize; }

    public String getServingUnit() { return servingUnit; }

    public void setServingUnit(String servingUnit) { this.servingUnit = servingUnit; }

    public double getCaloridPerAmount() { return caloridPerAmount; }

    public void setCaloridPerAmount(double caloridPerAmount) { this.caloridPerAmount = caloridPerAmount; }

    public double getProteinPerAmount() { return proteinPerAmount; }

    public void setProteinPerAmount(double proteinPerAmount) { this.proteinPerAmount = proteinPerAmount; }

    public double getFatPerAmount() { return fatPerAmount; }

    public void setFatPerAmount(double fatPerAmount) { this.fatPerAmount = fatPerAmount; }

    public double getCarbohydratePerAmount() { return carbohydratePerAmount; }

    public void setCarbohydratePerAmount(double carbohydratePerAmount) { this.carbohydratePerAmount = carbohydratePerAmount; }
}
