package com.example.sensitive_coach.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Repository.DietMenuRepository;
import com.example.sensitive_coach.Room.Entity.DietMenu;

import java.util.List;

public class DietMenuViewModel extends AndroidViewModel {

    private final DietMenuRepository dietMenuRepository;
    private LiveData<List<DietMenu>> dietMenuList;

    public DietMenuViewModel(Application application) {

        super(application);
        dietMenuRepository = new DietMenuRepository(application);
        dietMenuList = new LiveData<List<DietMenu>>() {};
    }

    // 식단 등록
    public void insertDietMenu(DietMenu dietMenu) {

        dietMenuRepository.insertDietMenu(dietMenu);
    }

    // 식단 수정
    public void updateDietMenu(DietMenu dietMenu) {

        dietMenuRepository.updateDietMenu(dietMenu);
    }

    // 식단 삭제
    public void deleteDietMenu(int dietMenuID) {

        dietMenuRepository.deleteDietMenu(dietMenuID);
    }

    // 식단 전체 삭제
    public void deleteAllDietMenu() {

        dietMenuRepository.deleteAllDietMenu();
    }

    // 식단 전체 조회
    public LiveData<List<DietMenu>> inquiryDietMenus() {

        dietMenuList = dietMenuRepository.inquiryDietMenus();

        return dietMenuList;
    }

    // 날짜별 식단 조회
    public LiveData<List<DietMenu>> inquiryDailyDietMenus(String intakeDate) {

        dietMenuList = dietMenuRepository.inquiryDailyDietMenus(intakeDate);

        return dietMenuList;
    }
}
