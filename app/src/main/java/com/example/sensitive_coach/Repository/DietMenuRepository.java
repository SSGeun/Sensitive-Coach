package com.example.sensitive_coach.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Room.AppDatabase.AppDatabase;
import com.example.sensitive_coach.Room.DAO.DietMenuDAO;
import com.example.sensitive_coach.Room.Entity.DietMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Repository: 여러 데이터 소스를 관리하는데 사용
public class DietMenuRepository {

    private final DietMenuDAO dietMenuDAO;
    private LiveData<List<DietMenu>> dietMenuList;
    private AppDatabase appDatabase;

    public DietMenuRepository(Application application) {

        appDatabase = AppDatabase.getAppDatabase(application);
        dietMenuDAO = appDatabase.dietMenuDAO();
        dietMenuList = new LiveData<List<DietMenu>>() {};
    }

    // 식단 추가
    public void insertDietMenu(DietMenu dietMenu) {

        // 메인 Thread에서 DB에 접근할 수 없으므로 AsyncTask 사용 (Background 작업)
        new AsyncTask<DietMenu, Void, Void>() {

            // doInBackground(): Background 작업 진행
            @Override
            protected Void doInBackground(DietMenu... dietMenus) {

                dietMenuDAO.insertDietMenu(dietMenus[0]);
                return null;
            }
        }.execute(dietMenu);
    }

    // 식단 수정
    public void updateDietMenu(DietMenu dietMenu) {

        new AsyncTask<DietMenu, Void, Void>() {

            @Override
            protected Void doInBackground(DietMenu... dietMenus) {

                dietMenuDAO.updateDietMenu(dietMenus[0]);
                return null;
            }
        }.execute(dietMenu);
    }

    // 식단 삭제
    public void deleteDietMenu(int dietMenuID) {

        new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                dietMenuDAO.deleteDietMenu(integers[0]);
                return null;
            }
        }.execute(dietMenuID);
    }

    // 식단 전체 삭제
    public void deleteAllDietMenu() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                dietMenuDAO.deleteAllDietMenu();
                return null;
            }
        }.execute();
    }

    // 식단 전체 조회
    public LiveData<List<DietMenu>> inquiryDietMenus() {

        dietMenuList = dietMenuDAO.inquiryDietMenus();

        return dietMenuList;
    }

    // 날짜별 식단 조회
    public LiveData<List<DietMenu>> inquiryDailyDietMenus(String intakeDate) {

        dietMenuList = dietMenuDAO.inquiryDailyDietMenus(intakeDate);

        return dietMenuList;
    }
}
