package com.example.sensitive_coach.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sensitive_coach.Repository.RoutineRepository;
import com.example.sensitive_coach.Room.Entity.Routine;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {

    private final RoutineRepository routineRepository;
    private final LiveData<List<Routine>> routineList;

    public RoutineViewModel(Application application) {

        super(application);
        routineRepository = new RoutineRepository(application);
        routineList = routineRepository.inquiryRoutines();
    }

    // 루틴 등록
    public void insertRoutine(Routine routine) {

        routineRepository.insertRoutine(routine);
    }

    // 루틴 수정
    public void updateRoutine(Routine routine) {

        routineRepository.updateRoutine(routine);
    }

    // 루틴 삭제
    public void deleteRoutine(int routineID) {

        routineRepository.deleteRoutine(routineID);
    }

    // 루틴 전체 조회
    public LiveData<List<Routine>> inquiryRoutines() {

        return routineList;
    }
}
