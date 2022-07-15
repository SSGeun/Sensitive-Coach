package com.example.sensitive_coach.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Room.AppDatabase.AppDatabase;
import com.example.sensitive_coach.Room.DAO.FoodDAO;
import com.example.sensitive_coach.Room.Entity.Food;

import java.util.List;

// Repository: 여러 데이터 소스를 관리하는데 사용
public class FoodRepository {

    private final FoodDAO foodDAO;
    private final LiveData<List<Food>> foodList;

    public FoodRepository(Application application) {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        foodDAO = appDatabase.foodDAO();
        foodList = foodDAO.inquiryFoods();
    }

    // 음식 등록 (어플리케이션 초기, 음식 목록을 로컬 DB에 추가)
    public void insertFoodList(List<Food> foodList) {

        // 메인 Thread에서 DB에 접근할 수 없으므로 AsyncTask 사용 (Background 작업)
        new AsyncTask<List<Food>, Void, Void>() {

            // doInBackground(): Background 작업 진행
            @Override
            protected Void doInBackground(List<Food>... lists) {

                for (int i = 0; i < lists.length; i++) {

                    foodDAO.insertFoodList(lists[i]);
                }
                return null;
            }
        }.execute(foodList);
    }

    // 음식 전체 조회
    public LiveData<List<Food>> inquiryFoods() {

        return foodList;
    }
}
