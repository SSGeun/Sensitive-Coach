package com.example.sensitive_coach;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.sensitive_coach.UIController.DietMenuGraphInquiryFragment;
import com.example.sensitive_coach.UIController.DietMenuInquiryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RoutineMainFragment routineMainFragment;
    DietMainFragment dietMainFragment;
    CalendarFragment calenderFragment;
    HomeSearchFragment homeSearchFragment;
    ExerciseInfoFragment exerciseInfoFragment;
    DietMenuInquiryFragment dietMenuInquiryFragment;
    DietMenuGraphInquiryFragment dietMenuGraphInquiryFragment;
    TrainingMainFragment trainingMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        routineMainFragment = new RoutineMainFragment();
        dietMainFragment = new DietMainFragment();
        calenderFragment = new CalendarFragment();
        dietMenuInquiryFragment = new DietMenuInquiryFragment();
        homeSearchFragment = new HomeSearchFragment();
        exerciseInfoFragment = new ExerciseInfoFragment();
        dietMenuGraphInquiryFragment = new DietMenuGraphInquiryFragment();
        trainingMainFragment = new TrainingMainFragment();

        // 현재 날짜 받아오기
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        final String year = String.valueOf(Integer.parseInt(yearFormat.format(currentTime)));
        final String month = String.valueOf(Integer.parseInt(monthFormat.format(currentTime)));
        final String day = String.valueOf(Integer.parseInt(dayFormat.format(currentTime)));

        final String currentDate = year + "." + month + "." + day;

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, homeSearchFragment).commitAllowingStateLoss(); //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, routineMainFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab2: {
                        Bundle bundle = new Bundle();
                        bundle.putString("currentDate", currentDate);
                        dietMenuInquiryFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, dietMenuInquiryFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, homeSearchFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab4: {

                        return true;
                    }
                    case R.id.tab5: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, calenderFragment).commitAllowingStateLoss();
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
    }

    public void sendInfo(Fragment fragment) {
        Bundle bundle = new Bundle();

        String param1 = "1";
        bundle.putString("param1", param1);
        String param2 = "2";
        bundle.putString("param2", param2);
        fragment.setArguments(bundle);// Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }

    public void replaceFragment(Fragment fragment) {

        sendInfo(fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment).commitAllowingStateLoss();

    }

    public void refresh_dietMenuInquiryFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(dietMenuInquiryFragment).attach(dietMenuInquiryFragment).commit();
    }

    public void refresh_dietMenuDailyInquiryFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(dietMenuGraphInquiryFragment).attach(dietMenuGraphInquiryFragment).commit();
    }

    public void refresh_trainingMainFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(trainingMainFragment).attach(trainingMainFragment).commit();
    }
}


