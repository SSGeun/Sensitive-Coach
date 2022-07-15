package com.example.sensitive_coach.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sensitive_coach.Room.Entity.DietMenu;

import java.util.List;

// DAO: 데이터 접근 객체. SQL 쿼리를 함수에 매핑하며, DAO를 사용하여 메소드를 호출하고 Room이 나머지를 처리한다.
// 식단 DAO
@Dao
public interface DietMenuDAO {

    // LiceData<>: DB 테이블에 변화가 생기면 해당 변화를 감지하고 백그라운드로 작업을 처리할 수 있음 (BluetoothConnection 에서 옵저버와 함께 사용.)
    // (참고) DB 접근 즉 CRUD 작업은 모두 백그라운드에서 실행되어야 한다.

    @Insert
    void insertDietMenu(DietMenu dietMenu);         // 식단 등록

    @Update
    void updateDietMenu(DietMenu dietMenu);         // 식단 수정

    @Query("DELETE FROM dietmenu WHERE dietMenuID = :dietMenuID")
    void deleteDietMenu(int dietMenuID);            // 식단 삭제

    @Query("DELETE FROM dietmenu")
    void deleteAllDietMenu();                       // 식단 전체 삭제

    @Query("SELECT * FROM dietmenu")
    LiveData<List<DietMenu>> inquiryDietMenus();     // 식단 전체 조회

    @Query("SELECT * FROM dietmenu WHERE intakeDate = :intakeDate ORDER BY intakeTime ASC")
    LiveData<List<DietMenu>> inquiryDailyDietMenus(String intakeDate);      // 식단 일별 조회

    // 식단 주별 조회, 식단 일별 조회, 식단 추천
}
