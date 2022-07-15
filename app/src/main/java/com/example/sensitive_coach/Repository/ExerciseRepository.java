package com.example.sensitive_coach.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Room.AppDatabase.AppDatabase;
import com.example.sensitive_coach.Room.DAO.ExerciseDAO;
import com.example.sensitive_coach.Room.Entity.Exercise;

import java.util.List;

// Repository: 여러 데이터 소스를 관리하는데 사용
public class ExerciseRepository {

    private final ExerciseDAO exerciseDAO;
    private final LiveData<List<Exercise>> exerciseList;

    public ExerciseRepository(Application application) {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        exerciseDAO = appDatabase.exerciseDAO();
        exerciseList = exerciseDAO.inquiryExercises();
    }

    // 운동 등록 (어플리케이션 초기, 운동 목록을 로컬 DB에 추가)
    public void insertExerciseList(List<Exercise> exerciseList) {

        // 메인 Thread에서 DB에 접근할 수 없으므로 AsyncTask 사용 (Background 작업)
        new AsyncTask<List<Exercise>, Void, Void>() {

            // doInBackground(): Background 작업 진행
            @Override
            protected Void doInBackground(List<Exercise>... lists) {

                for (int i = 0; i < lists.length; i++) {

                    exerciseDAO.insertExerciseList(lists[i]);
                }
                return null;
            }
        }.execute(exerciseList);
    }

    // 운동 전체 조회
    public LiveData<List<Exercise>> inquiryExercises() {

        return exerciseList;
    }
}
