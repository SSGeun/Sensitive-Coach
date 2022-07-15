package com.example.sensitive_coach.UIController;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.sensitive_coach.Adapter.ListViewAdapter_DietMenuInquiry;
import com.example.sensitive_coach.MainActivity;
import com.example.sensitive_coach.R;
import com.example.sensitive_coach.Room.Entity.DietMenu;
import com.example.sensitive_coach.ViewModel.DietMenuViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DietMenuInquiryFragment extends Fragment {

    private DietMenuGraphInquiryFragment dietMenuGraphInquiryFragment;
    private DietMenuRegisterFragment dietMenuRegisterFragment;

    private boolean changeDate;

    private String datePickerYear;
    private String datePickerMonth;
    private String datePickerDay;

    private String currentDate;

    private TextView dietMenuInquiryTotalCalorie;
    private double totalCalorie;

    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelStore viewModelStore = new ViewModelStore();
    private DietMenuViewModel dietMenuViewModel;

    public static DietMenuInquiryFragment newInstance() {
        return new DietMenuInquiryFragment();
    }

    private ViewGroup viewGroup;
    private Button Button;

    private ListView dietMenuInquiryItemList;       // 식단 조회 아이템 리스트
    private ListViewAdapter_DietMenuInquiry listViewAdapterDietMenuInquiry;     // 식단 조회 아이템 리스트 어댑터

    private ArrayList<DietMenu> dietMenuArrayList;        // 사용자 식단 리스트

    private TextView dietMenuInquiryDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.diet_menu_inquiry_fragment, container, false);

        dietMenuInquiryDate = (TextView) viewGroup.findViewById(R.id.dietMenuInquiryDate);
        dietMenuInquiryTotalCalorie = (TextView) viewGroup.findViewById(R.id.dietMenuInquiryTotalCalorie);

        totalCalorie = 0;

        if (!changeDate) {

            currentDate = getArguments().getString("currentDate");
            changeDate = true;
        }

        dietMenuInquiryDate.setText(currentDate);

        String[] splitDate = currentDate.split("\\.");
        datePickerYear = splitDate[0];
        datePickerMonth = splitDate[1];
        datePickerDay = splitDate[2];

        dietMenuInquiryDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, Integer.parseInt(datePickerYear), Integer.parseInt(datePickerMonth), Integer.parseInt(datePickerDay));
                datePickerDialog.show();

                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        ((MainActivity) getActivity()).refresh_dietMenuInquiryFragment();
                    }
                });
            }
        });

        Button b = (Button) viewGroup.findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 버튼 클릭시 팝업 메뉴가 나오게 하기
                // PopupMenu 는 API 11 레벨부터 제공한다
                PopupMenu p = new PopupMenu(
                        getActivity(), // 현재 화면의 제어권자
                        v); // anchor : 팝업을 띄울 기준될 위젯
                p.getMenuInflater().inflate(R.menu.menu_dietpopup, p.getMenu());

                dietMenuGraphInquiryFragment = new DietMenuGraphInquiryFragment();

                // 이벤트 처리
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.dailyButton:
                                Bundle bundle = new Bundle();

                                bundle.putString("currentDate", currentDate);
                                dietMenuGraphInquiryFragment.setArguments(bundle);

                                getActivity().getSupportFragmentManager().beginTransaction().remove(DietMenuInquiryFragment.this).commit();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, dietMenuGraphInquiryFragment).commitAllowingStateLoss();
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

        // 식단 조회 할 때, 해당 날짜의 시간 순으로 정렬하여 조회하기!
        if (viewModelFactory == null) {

            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
        }

        dietMenuViewModel = new ViewModelProvider(this, viewModelFactory).get(DietMenuViewModel.class);
        dietMenuArrayList = new ArrayList<DietMenu>();

        //dietMenuViewModel.deleteAllDietMenu();

        dietMenuViewModel.inquiryDailyDietMenus(currentDate).observe(getViewLifecycleOwner(), new Observer<List<DietMenu>>() {

            @Override
            public void onChanged(List<DietMenu> dietMenus) {

                dietMenuArrayList.addAll(dietMenus);

                dietMenuInquiryItemList = (ListView) viewGroup.findViewById(R.id.dietMenuInquiryItemList);

                listViewAdapterDietMenuInquiry = new ListViewAdapter_DietMenuInquiry(dietMenuArrayList, getActivity());
                dietMenuInquiryItemList.setAdapter(listViewAdapterDietMenuInquiry);

                for (int i = 0; i < dietMenuArrayList.size(); i++) {

                    totalCalorie += dietMenuArrayList.get(i).getTotalCalorie();
                }

                dietMenuInquiryTotalCalorie.setText(String.valueOf(totalCalorie) + " kcal");
            }
        });

        FloatingActionButton fab = viewGroup.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new FABClickListener());

        return viewGroup;
    }

    class FABClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            dietMenuRegisterFragment = new DietMenuRegisterFragment();
            getActivity().getSupportFragmentManager().beginTransaction().remove(DietMenuInquiryFragment.this).commit();
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

