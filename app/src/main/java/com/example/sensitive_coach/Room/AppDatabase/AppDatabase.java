package com.example.sensitive_coach.Room.AppDatabase;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sensitive_coach.Room.DAO.DietMenuDAO;
import com.example.sensitive_coach.Room.DAO.ExerciseDAO;
import com.example.sensitive_coach.Room.DAO.FoodDAO;
import com.example.sensitive_coach.Room.DAO.ManualDAO;
import com.example.sensitive_coach.Room.DAO.RoutineDAO;
import com.example.sensitive_coach.Room.Entity.DietMenu;
import com.example.sensitive_coach.Room.Entity.Exercise;
import com.example.sensitive_coach.Room.Entity.Food;
import com.example.sensitive_coach.Room.Entity.Manual;
import com.example.sensitive_coach.Room.Entity.Routine;
import com.example.sensitive_coach.Room.Converter.Converters;

// RoomDatabase: 데이터베이스 작업을 단순화하고 기본 SQLite 데이터베이스에 대한 액세스 지점 역할. (DAO를 사용하여 SQLite 데이터베이스에 쿼리를 실행)
@Database(entities = {Exercise.class, Manual.class, Routine.class, Food.class, DietMenu.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    // 데이터베이스를 매번 생성하는건 리소스를 많이 사용하므로 싱글톤 사용
    private static AppDatabase INSTANCE;

    public abstract ExerciseDAO exerciseDAO();

    public abstract ManualDAO manualDAO();

    public abstract RoutineDAO routineDAO();

    public abstract FoodDAO foodDAO();

    public abstract DietMenuDAO dietMenuDAO();

    // DB 객체 생성
    public static AppDatabase getAppDatabase(Application application) {

        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(application, AppDatabase.class, "Sensitive_Coach.db")
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .fallbackToDestructiveMigrationFrom(1, 2, 3, 4)
                    .build();
        }

        return INSTANCE;
    }

    // DB 객체 제거
    public static void destroyInstance() {

        INSTANCE = null;
    }
}