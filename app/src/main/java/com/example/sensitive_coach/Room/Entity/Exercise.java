package com.example.sensitive_coach.Room.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entity: Room 작업시 데이터베이스 테이블을 설명하는 주석이 달린 클래스
// 운동 Entity
@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)        // autoGenerate: insert 과정에서 exerciseID 값 자동 증가
    @ColumnInfo(name = "exerciseID")
    public int exerciseID;      // 운동 ID

    @NonNull
    @ColumnInfo(name = "exerciseClassification")
    public String exerciseClassification;       // 운동 분류

    @NonNull
    @ColumnInfo(name = "exerciseName")
    public String exerciseName;     // 운동 이름

    @NonNull
    @ColumnInfo(name = "exerciseVideoPath")
    public String exerciseVideoPath;        // 운동 영상 경로

    @NonNull
    @ColumnInfo(name = "numberOfSets")
    public int numberOfSets;        // 권장 세트 수

    @NonNull
    @ColumnInfo(name = "numberPerSet")
    public int numberPerSet;        // 세트 당 권장 횟수

    public int getExerciseID() { return exerciseID; }

    public void setExerciseID(int exerciseID) { this.exerciseID = exerciseID; }

    @NonNull
    public String getExerciseClassification() { return exerciseClassification; }

    public void setExerciseClassification(@NonNull String exerciseClassification) { this.exerciseClassification = exerciseClassification; }

    @NonNull
    public String getExerciseName() { return exerciseName; }

    public void setExerciseName(@NonNull String exerciseName) { this.exerciseName = exerciseName; }

    @NonNull
    public String getExerciseVideoPath() { return exerciseVideoPath; }

    public void setExerciseVideoPath(@NonNull String exerciseVideoPath) { this.exerciseVideoPath = exerciseVideoPath; }

    public int getNumberOfSets() { return numberOfSets; }

    public void setNumberOfSets(int numberOfSets) { this.numberOfSets = numberOfSets; }

    public int getNumberPerSet() { return numberPerSet; }

    public void setNumberPerSet(int numberPerSet) { this.numberPerSet = numberPerSet; }
}
