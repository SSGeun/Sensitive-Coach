package com.example.sensitive_coach.Room.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Entity: Room 작업시 데이터베이스 테이블을 설명하는 주석이 달린 클래스
// 교본 Entity
@Entity(foreignKeys = @ForeignKey(entity = Exercise.class, parentColumns = "exerciseID", childColumns = "exerciseID"))
public class Manual {

    @PrimaryKey
    @ColumnInfo(name = "exerciseID")
    public int exerciseID;      // 운동 ID

    @ColumnInfo(name = "firstSensorValue")
    public double firstSensorValue;     // 1번 센서의 값

    @ColumnInfo(name = "secondSensorValue")
    public double secondSensorValue;     // 2번 센서의 값

    @ColumnInfo(name = "thirdSensorValue")
    public double thirdSensorValue;     // 3번 센서의 값

    @ColumnInfo(name = "fourthSensorValue")
    public double fourthSensorValue;     // 4번 센서의 값

    @ColumnInfo(name = "fifthSensorValue")
    public double fifthSensorValue;     // 5번 센서의 값

    public int getExerciseID() { return exerciseID; }

    public void setExerciseID(int exerciseID) { this.exerciseID = exerciseID; }

    public double getFirstSensorValue() { return firstSensorValue; }

    public void setFirstSensorValue(double firstSensorValue) { this.firstSensorValue = firstSensorValue; }

    public double getSecondSensorValue() { return secondSensorValue; }

    public void setSecondSensorValue(double secondSensorValue) { this.secondSensorValue = secondSensorValue; }

    public double getThirdSensorValue() { return thirdSensorValue; }

    public void setThirdSensorValue(double thirdSensorValue) { this.thirdSensorValue = thirdSensorValue; }

    public double getFourthSensorValue() { return fourthSensorValue; }

    public void setFourthSensorValue(double fourthSensorValue) { this.fourthSensorValue = fourthSensorValue; }

    public double getFifthSensorValue() { return fifthSensorValue; }

    public void setFifthSensorValue(double fifthSensorValue) { this.fifthSensorValue = fifthSensorValue; }
}
