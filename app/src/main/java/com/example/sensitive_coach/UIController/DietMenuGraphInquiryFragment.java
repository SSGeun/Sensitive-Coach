package com.example.sensitive_coach.UIController;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.sensitive_coach.MainActivity;
import com.example.sensitive_coach.R;
import com.example.sensitive_coach.Room.Entity.DietMenu;
import com.example.sensitive_coach.ViewModel.DietMenuViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DietMenuGraphInquiryFragment extends Fragment {

    public static DietMenuGraphInquiryFragment newInstance() {

        return new DietMenuGraphInquiryFragment();
    }

    private TextView dietMenuDailyInquiryDate;
    private TextView dietMenuDailyInquiryTotalCalorie;

    private Button dietMenuDailyInquiryButton;

    private FloatingActionButton dailyFloatingActionButton;

    private DietMenuInquiryFragment dietMenuInquiryFragment;
    private DietMenuRegisterFragment dietMenuRegisterFragment;

    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelStore viewModelStore = new ViewModelStore();
    private DietMenuViewModel dietMenuViewModel;

    private BarChart barChart;
    private ArrayList<DietMenu> dietMenuArrayList;        // 사용자 식단 리스트

    private ArrayList<BarEntry> proteinArrayList;           // 단백질 데이터
    private ArrayList<BarEntry> fatArrayList;               // 지방 그룹 데이터
    private ArrayList<BarEntry> crabohydrateArrayList;      // 탄수화물 그룹 데이터

    // 1 그룹 단백질, 지방, 탄수화물 합산
    private double oneGroupProtein = 0;
    private double oneGroupFat = 0;
    private double oneGroupCrabohydrate = 0;

    // 2 그룹 단백질, 지방, 탄수화물 합산
    private double twoGroupProtein = 0;
    private double twoGroupFat = 0;
    private double twoGroupCrabohydrate = 0;

    // 3 그룹 단백질, 지방, 탄수화물 합산
    private double threeGroupProtein = 0;
    private double threeGroupFat = 0;
    private double threeGroupCrabohydrate = 0;

    // 4 그룹 단백질, 지방, 탄수화물 합산
    private double fourGroupProtein = 0;
    private double fourGroupFat = 0;
    private double fourGroupCrabohydrate = 0;

    // 총 하루 섭취 칼로리
    private double totalCalorie;

    private String datePickerYear;
    private String datePickerMonth;
    private String datePickerDay;

    private String currentDate;
    private boolean changeDate;

    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.diet_menu_graph_inquiry_fragment, container, false);

        dietMenuDailyInquiryTotalCalorie = (TextView) viewGroup.findViewById(R.id.dietMenuDailyInquiryTotalCalorie);
        dietMenuDailyInquiryDate = (TextView) viewGroup.findViewById(R.id.dietMenuDailyInquiryDate);

        dietMenuDailyInquiryButton = (Button) viewGroup.findViewById(R.id.dietMenuDailyInquiryButton);

        totalCalorie = 0;

        if (!changeDate) {

            currentDate = getArguments().getString("currentDate");
            changeDate = true;
        }

        dietMenuDailyInquiryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 버튼 클릭시 팝업 메뉴가 나오게 하기
                // PopupMenu 는 API 11 레벨부터 제공한다
                PopupMenu p = new PopupMenu(
                        getActivity(), // 현재 화면의 제어권자
                        v); // anchor : 팝업을 띄울 기준될 위젯
                p.getMenuInflater().inflate(R.menu.menu_dietpopup, p.getMenu());

                dietMenuInquiryFragment = new DietMenuInquiryFragment();

                // 이벤트 처리
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.dailyButton:
                                Bundle bundle = new Bundle();

                                bundle.putString("currentDate", currentDate);
                                dietMenuInquiryFragment.setArguments(bundle);

                                getActivity().getSupportFragmentManager().beginTransaction().remove(DietMenuGraphInquiryFragment.this).commit();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, dietMenuInquiryFragment).commitAllowingStateLoss();
                                break;

                            case R.id.weeklyButton:
                                break;

                        }

                        return false;
                    }
                });
                p.show(); // 메뉴를 띄우기
            }
        });

        dietMenuDailyInquiryDate.setText(currentDate);

        String[] splitDate = currentDate.split("\\.");
        datePickerYear = splitDate[0];
        datePickerMonth = splitDate[1];
        datePickerDay = splitDate[2];

        dietMenuDailyInquiryDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, Integer.parseInt(datePickerYear), Integer.parseInt(datePickerMonth), Integer.parseInt(datePickerDay));
                datePickerDialog.show();

                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        ((MainActivity) getActivity()).refresh_dietMenuDailyInquiryFragment();
                    }
                });
            }
        });

        if (viewModelFactory == null) {

            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
        }

        dietMenuViewModel = new ViewModelProvider(this, viewModelFactory).get(DietMenuViewModel.class);
        dietMenuArrayList = new ArrayList<DietMenu>();

        dietMenuViewModel.inquiryDailyDietMenus(currentDate).observe(getViewLifecycleOwner(), new Observer<List<DietMenu>>() {

            @Override
            public void onChanged(List<DietMenu> dietMenus) {

                dietMenuArrayList.addAll(dietMenus);

                barChart = (BarChart) viewGroup.findViewById(R.id.barchart);

                proteinArrayList = new ArrayList<>();           // 단백질 데이터
                fatArrayList = new ArrayList<>();               // 지방 그룹 데이터
                crabohydrateArrayList = new ArrayList<>();      // 탄수화물 그룹 데이터

                for (int i = 0; i < dietMenuArrayList.size(); i++) {

                    String[] splitTime = dietMenuArrayList.get(i).getIntakeTime().split(":");

                    // 1 그룹 데이터 및 총 칼로리 저장
                    if (splitTime[0].equals("04") || splitTime[0].equals("05") || splitTime[0].equals("06") || splitTime[0].equals("07") || splitTime[0].equals("08") || splitTime[0].equals("09")) {

                        oneGroupProtein += dietMenuArrayList.get(i).getTotalProtein();
                        oneGroupFat += dietMenuArrayList.get(i).getTotalFat();
                        oneGroupCrabohydrate += dietMenuArrayList.get(i).getTotalCarbohydrate();

                        totalCalorie += dietMenuArrayList.get(i).getTotalCalorie();
                    }

                    // 2 그룹 데이터 및 총 칼로리 저장
                    else if (splitTime[0].equals("10") || splitTime[0].equals("11") || splitTime[0].equals("12") || splitTime[0].equals("13") || splitTime[0].equals("14") || splitTime[0].equals("15")) {

                        twoGroupProtein += dietMenuArrayList.get(i).getTotalProtein();
                        twoGroupFat += dietMenuArrayList.get(i).getTotalFat();
                        twoGroupCrabohydrate += dietMenuArrayList.get(i).getTotalCarbohydrate();

                        totalCalorie += dietMenuArrayList.get(i).getTotalCalorie();
                    }

                    // 3 그룹 데이터 및 총 칼로리 저장
                    else if (splitTime[0].equals("16") || splitTime[0].equals("17") || splitTime[0].equals("18") || splitTime[0].equals("19") || splitTime[0].equals("20") || splitTime[0].equals("21")) {

                        threeGroupProtein += dietMenuArrayList.get(i).getTotalProtein();
                        threeGroupFat += dietMenuArrayList.get(i).getTotalFat();
                        threeGroupCrabohydrate += dietMenuArrayList.get(i).getTotalCarbohydrate();

                        totalCalorie += dietMenuArrayList.get(i).getTotalCalorie();
                    }

                    // 4 그룹 데이터 및 총 칼로리 저장
                    else if (splitTime[0].equals("22") || splitTime[0].equals("23") || splitTime[0].equals("00") || splitTime[0].equals("01") || splitTime[0].equals("02") || splitTime[0].equals("03")) {

                        fourGroupProtein += dietMenuArrayList.get(i).getTotalProtein();
                        fourGroupFat += dietMenuArrayList.get(i).getTotalFat();
                        fourGroupCrabohydrate += dietMenuArrayList.get(i).getTotalCarbohydrate();

                        totalCalorie += dietMenuArrayList.get(i).getTotalCalorie();
                    }
                }

                // 단백질 그룹 저장
                proteinArrayList.add(new BarEntry((float)oneGroupProtein, 0));
                proteinArrayList.add(new BarEntry((float)twoGroupProtein, 1));
                proteinArrayList.add(new BarEntry((float)threeGroupProtein, 2));
                proteinArrayList.add(new BarEntry((float)fourGroupProtein, 3));

                // 지방 그룹 저장
                fatArrayList.add(new BarEntry((float)oneGroupFat, 0));
                fatArrayList.add(new BarEntry((float)twoGroupFat, 1));
                fatArrayList.add(new BarEntry((float)threeGroupFat, 2));
                fatArrayList.add(new BarEntry((float)fourGroupFat, 3));

                // 탄수화물 그룹 저장
                crabohydrateArrayList.add(new BarEntry((float)oneGroupCrabohydrate, 0));
                crabohydrateArrayList.add(new BarEntry((float)twoGroupCrabohydrate, 1));
                crabohydrateArrayList.add(new BarEntry((float)threeGroupCrabohydrate, 2));
                crabohydrateArrayList.add(new BarEntry((float)fourGroupCrabohydrate, 3));

                // 총 칼로리
                dietMenuDailyInquiryTotalCalorie.setText(String.valueOf(totalCalorie) + " kcal");

                ArrayList<String> time = new ArrayList<>();

                // 1 그룹
                time.add("4-9");

                // 2 그룹
                time.add("10-15");

                // 3 그룹
                time.add("16-21");

                // 4 그룹
                time.add("22-3");

                BarDataSet proteinDataSet = new BarDataSet (proteinArrayList, "단백질");
                BarDataSet fatDataSet = new BarDataSet (fatArrayList, "지방");
                BarDataSet crabohydrateDataSet = new BarDataSet (crabohydrateArrayList, "탄수화물");

                proteinDataSet.setColors(Collections.singletonList(Color.rgb(52,152, 219)));
                fatDataSet.setColors(Collections.singletonList(Color.rgb(26,188, 156)));
                crabohydrateDataSet.setColors(Collections.singletonList(Color.rgb(41,128, 185)));

                ArrayList<BarDataSet> barDataSetArrayList = new ArrayList<>();
                barDataSetArrayList.add(proteinDataSet);
                barDataSetArrayList.add(fatDataSet);
                barDataSetArrayList.add(crabohydrateDataSet);
                barChart.animateY(5000);

                BarData barData = new BarData(time, (ArrayList)barDataSetArrayList);
                barChart.setData(barData);
            }
        });

        dailyFloatingActionButton = (FloatingActionButton) viewGroup.findViewById(R.id.dailyFloatingActionButton);
        dailyFloatingActionButton.setOnClickListener(new FABClickListener2());

        return viewGroup;
    }

    class FABClickListener2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            dietMenuRegisterFragment = new DietMenuRegisterFragment();
            getActivity().getSupportFragmentManager().beginTransaction().remove(DietMenuGraphInquiryFragment.this).commit();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, dietMenuRegisterFragment).commitAllowingStateLoss();
        }
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            datePickerYear = String.valueOf(year);
            datePickerMonth = String.valueOf(month);
            datePickerDay = String.valueOf(dayOfMonth);

            currentDate = datePickerYear + "." + datePickerMonth + "." + datePickerDay;
        }
    };
}