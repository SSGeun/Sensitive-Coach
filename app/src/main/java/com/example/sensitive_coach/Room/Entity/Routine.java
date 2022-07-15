package com.example.sensitive_coach.Room.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entity: Room 작업시 데이터베이스 테이블을 설명하는 주석이 달린 클래스
// 루틴 Entity
@Entity
public class Routine {

    @PrimaryKey(autoGenerate = true)        // autoGenerate: insert 과정에서 routineID 값 자동 증가
    @ColumnInfo(name = "routineID")
    public int routineID;       // 루틴 ID

    @NonNull
    @ColumnInfo(name = "routineName")
    public String routineName;      // 루틴 이름

    @ColumnInfo(name = "exerciseID")
    public int exerciseID;      // 운동 ID

    public int getRoutineID() { return routineID; }

    public void setRoutineID(int routineID) { this.routineID = routineID; }

    @NonNull
    public String getRoutineName() { return routineName; }

    public void setRoutineName(@NonNull String routineName) { this.routineName = routineName; }

    public int getExerciseID() { return exerciseID; }

    public void setExerciseID(int exerciseID) { this.exerciseID = exerciseID; }
}
