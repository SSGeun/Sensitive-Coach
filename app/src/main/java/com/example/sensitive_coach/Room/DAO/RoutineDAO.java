package com.example.sensitive_coach.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sensitive_coach.Room.Entity.Routine;

import java.util.List;

// DAO: 데이터 접근 객체. SQL 쿼리를 함수에 매핑하며, DAO를 사용하여 메소드를 호출하고 Room이 나머지를 처리한다.
// 루틴 DAO
@Dao
public interface RoutineDAO {

    // LiceData<>: DB 테이블에 변화가 생기면 해당 변화를 감지하고 백그라운드로 작업을 처리할 수 있음 (BluetoothConnection 에서 옵저버와 함께 사용.)
    // (참고) DB 접근 즉 CRUD 작업은 모두 백그라운드에서 실행되어야 한다.

    @Insert
    void insertRoutine(Routine routine);            // 루틴 등록

    @Update
    void updateRoutine(Routine routine);            // 루틴 수정

    @Query("DELETE FROM routine WHERE routineID = :routineID")
    void deleteRoutine(int routineID);              // 루틴 삭제

    @Query("SELECT * FROM routine")
    LiveData<List<Routine>> inquiryRoutines();      // 루틴 전체 조회
}
