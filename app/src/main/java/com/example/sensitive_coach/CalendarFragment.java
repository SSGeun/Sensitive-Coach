package com.example.sensitive_coach;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.example.sensitive_coach.UIController.DietMenuRegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private CalendarView calendarView;

    ViewGroup viewGroup;
    BottomNavigationView bottomNavigationView;
    RoutineMainFragment routineMainFragment;
    DietMainFragment dietMainFragment;
    CalendarFragment calenderActivity;
    HomeSearchFragment homeSearchFragment;
    ExerciseInfoFragment exerciseInfoFragment;
    DietMenuRegisterFragment dietMenuRegisterFragment;

    @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.calender_main,container,false);


      //  setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

       // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        initViews();
        return viewGroup;
    }

    private void initViews() {

        calendarView = (CalendarView) viewGroup.findViewById(R.id.calendar_view);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);

        ((RadioGroup) viewGroup.findViewById(R.id.rg_selection_type)).setOnCheckedChangeListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.show_selections:
                List<Calendar> days = calendarView.getSelectedDates();

                String result = "";
                for (int i = 0; i < days.size(); i++) {
                    Calendar calendar = days.get(i);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int month = calendar.get(Calendar.MONTH);
                    final int year = calendar.get(Calendar.YEAR);
                    String week = new SimpleDateFormat("EE").format(calendar.getTime());
                    String day_full = year + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일";
                    result += (day_full + "\n");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        clearSelectionsMenuClick();
        switch (checkedId) {

            case R.id.rb_single:
                calendarView.setSelectionType(SelectionType.SINGLE);
                break;

            case R.id.rb_multiple:
                calendarView.setSelectionType(SelectionType.MULTIPLE);
                break;

            case R.id.rb_range:
                calendarView.setSelectionType(SelectionType.RANGE);
                break;

            case R.id.rb_none:
                calendarView.setSelectionType(SelectionType.NONE);
                break;
        }
    }
}
