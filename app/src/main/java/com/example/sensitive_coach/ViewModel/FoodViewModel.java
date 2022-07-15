package com.example.sensitive_coach.ViewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Repository.FoodRepository;
import com.example.sensitive_coach.Room.Entity.Food;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private final FoodRepository foodRepository;
    private final LiveData<List<Food>> foodList;

    public FoodViewModel(Application application) {

        super(application);
        foodRepository = new FoodRepository(application);
        foodList = foodRepository.inquiryFoods();
    }

    // 음식 목록 등록 (어플리케이션 초기, 음식 목록을 로컬 DB에 추가)
    public void insertFoodList(List<Food> foodList) {

        foodRepository.insertFoodList(foodList);
    }

    // 음식 전체 조회
    public LiveData<List<Food>> inquiryFoods() {

        return foodList;
    }
}
