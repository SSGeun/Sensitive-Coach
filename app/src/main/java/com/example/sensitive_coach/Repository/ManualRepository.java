package com.example.sensitive_coach.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Room.AppDatabase.AppDatabase;
import com.example.sensitive_coach.Room.DAO.ManualDAO;
import com.example.sensitive_coach.Room.Entity.Manual;

import java.util.List;

// Repository: 여러 데이터 소스를 관리하는데 사용
public class ManualRepository {

    private final ManualDAO manualDAO;
    private final LiveData<List<Manual>> manualList;

    public ManualRepository(Application application) {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        manualDAO = appDatabase.manualDAO();
        manualList = manualDAO.inquiryManuals();
    }

    // 교본 목록 등록 (어플리케이션 초기, 교본 목록을 로컬 DB에 추가)
    public void insertManualList(List<Manual> manualList) {

        // 메인 Thread에서 DB에 접근할 수 없으므로 AsyncTask 사용 (Background 작업)
        new AsyncTask<List<Manual>, Void, Void>() {

            // doInBackground(): Background 작업 진행
            @Override
            protected Void doInBackground(List<Manual>... lists) {

                for (int i = 0; i < lists.length; i++) {

                    manualDAO.insertManualList(lists[i]);
                }
                return null;
            }
        }.execute(manualList);
    }

    // 교본 전체 조회
    public LiveData<List<Manual>> inquiryManuals() {

        return manualList;
    }
}
