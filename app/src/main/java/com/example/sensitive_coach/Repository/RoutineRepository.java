package com.example.sensitive_coach.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Room.AppDatabase.AppDatabase;
import com.example.sensitive_coach.Room.DAO.RoutineDAO;
import com.example.sensitive_coach.Room.Entity.Routine;

import java.util.List;

// Repository: 여러 데이터 소스를 관리하는데 사용
public class RoutineRepository {

    private final RoutineDAO routineDAO;
    private final LiveData<List<Routine>> routineList;

    public RoutineRepository(Application application) {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        routineDAO = appDatabase.routineDAO();
        routineList = routineDAO.inquiryRoutines();
    }

    // 루틴 등록
    public void insertRoutine(Routine routine) {

        // 메인 Thread에서 DB에 접근할 수 없으므로 AsyncTask 사용 (Background 작업)
        new AsyncTask<Routine, Void, Void>() {

            // doInBackground(): Background 작업 진행
            @Override
            protected Void doInBackground(Routine... routines) {

                routineDAO.insertRoutine(routines[0]);
                return null;
            }
        }.execute(routine);
    }

    // 루틴 수정
    public void updateRoutine(Routine routine) {

        new AsyncTask<Routine, Void, Void>() {

            @Override
            protected Void doInBackground(Routine... routines) {

                routineDAO.updateRoutine(routines[0]);
                return null;
            }
        }.execute(routine);
    }

    // 루틴 삭제
    public void deleteRoutine(int routineID) {

        new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                routineDAO.deleteRoutine(integers[0]);
                return null;
            }
        }.execute(routineID);
    }

    // 루틴 전체 조회
    public LiveData<List<Routine>> inquiryRoutines() {

        return routineList;
    }
}
