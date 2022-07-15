package com.example.sensitive_coach.UIController;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;


import com.example.sensitive_coach.Adapter.ListViewAdapter_DietMenuFoodSearch;
import com.example.sensitive_coach.Adapter.ListViewAdapter_DietMenuRegister;
import com.example.sensitive_coach.CSVFile.CSVFileReader;
import com.example.sensitive_coach.CalendarFragment;
import com.example.sensitive_coach.Model.DietMenu;
import com.example.sensitive_coach.R;
import com.example.sensitive_coach.Room.Entity.Food;
import com.example.sensitive_coach.ViewModel.DietMenuViewModel;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DietMenuRegisterFragment extends Fragment {

    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelStore viewModelStore;
    private DietMenuViewModel dietMenuViewModel;

    private EditText foodName;
    private EditText servings;
    private TextView timeAte;

    private ImageButton addDietMenu;

    private Button registerDietMenu;
    private Button cancelDietMenu;

    private int intFoodID;
    private String strFoodName;
    private double douServings;
    private double douCalorie;
    private String strTimeAte;
    private double douProtein;
    private double douFat;
    private double douCarbohydrate;

    private ListView dietMenuRegisterListView;
    private ListView dietMenuSearchListView;
    private ListViewAdapter_DietMenuRegister listViewAdapterDietMenuRegister;
    private ListViewAdapter_DietMenuFoodSearch listViewAdapterDietMenuFoodSearch;

    private ArrayList<Food> foodArrayList;
    private ArrayList<Food> tmpArrayList;
    ViewGroup viewGroup;

    DietMenuInquiryFragment dietMenuInquiryFragment;
    public static DietMenuRegisterFragment newInstance()
    {
        return new DietMenuRegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup)inflater.inflate(R.layout.diet_menu_register_fragment, container,false);
        dietMenuRegisterListView = (ListView) viewGroup.findViewById(R.id.dietMenuRegisterListView);
        dietMenuSearchListView = (ListView) viewGroup.findViewById(R.id.dietMenuSearchListView);

        dietMenuInquiryFragment = new DietMenuInquiryFragment();

        foodName = (EditText) viewGroup.findViewById(R.id.foodName);      // 음식 이름
        servings = (EditText)viewGroup.findViewById(R.id.servings);      // 수량
        timeAte = (TextView) viewGroup.findViewById(R.id.timeAte);        // 먹은 시간

        addDietMenu = (ImageButton) viewGroup.findViewById(R.id.addDietMenu); // 식단 추가

        registerDietMenu = (Button) viewGroup.findViewById(R.id.registerDietMenu);    // 식단 등록
        cancelDietMenu = (Button) viewGroup.findViewById(R.id.cancelDietMenu); // 식단 등록 취소

        listViewAdapterDietMenuRegister = new ListViewAdapter_DietMenuRegister();
        dietMenuRegisterListView.setAdapter(listViewAdapterDietMenuRegister);

        // 여기가 아니라 로딩 액티비티나 다른 곳에서 작업하는 것이 맞는거 같네유
        // Food CSV File 읽어와 DB에 저장
        InputStream inputStream = getResources().openRawResource(R.raw.food_data);
        CSVFileReader csvFileReader = new CSVFileReader(inputStream);
        List foodDataList = csvFileReader.read();

        foodArrayList = new ArrayList<Food>();

        for (int i = 0; i < foodDataList.size(); i++) {

            Object[] foodData = (Object[]) foodDataList.get(i);

            String foodName = (String) foodData[0];
            String foodGroupName = (String) foodData[1];
            double servingSize = Double.parseDouble((String) foodData[2]);
            String servingUnit = (String) foodData[3];
            double caloridPerAmount = Double.parseDouble((String) foodData[4]);
            double proteinPerAmount = Double.parseDouble((String) foodData[5]);
            double fatPerAmount = Double.parseDouble((String) foodData[6]);
            double carbohydratePerAmount = Double.parseDouble((String) foodData[7]);

            Food food = new Food(foodName, foodGroupName, servingSize, servingUnit, caloridPerAmount, proteinPerAmount, fatPerAmount, carbohydratePerAmount);

            foodArrayList.add(food);
        }

        tmpArrayList = new ArrayList<Food>();
        tmpArrayList.addAll(foodArrayList);

        listViewAdapterDietMenuFoodSearch = new ListViewAdapter_DietMenuFoodSearch(foodArrayList, getActivity());
        dietMenuSearchListView.setAdapter(listViewAdapterDietMenuFoodSearch);

        dietMenuSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String clickFoodName = listViewAdapterDietMenuFoodSearch.getClickFoodName(position);

                foodName.setText(clickFoodName);

                dietMenuRegisterListView.setVisibility(View.VISIBLE);
                dietMenuSearchListView.setVisibility(View.GONE);
            }
        });

        // 음식 이름 입력
        foodName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = foodName.getText().toString();
                search(text);
            }
        });

        foodName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dietMenuSearchListView.setVisibility(View.VISIBLE);
                dietMenuRegisterListView.setVisibility(View.GONE);
            }
        });

        // 수량 입력
        servings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dietMenuRegisterListView.setVisibility(View.VISIBLE);
                dietMenuSearchListView.setVisibility(View.GONE);
            }
        });

        // 먹은 시간 입력
        timeAte.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dietMenuRegisterListView.setVisibility(View.VISIBLE);
                dietMenuSearchListView.setVisibility(View.GONE);

                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (minute == 0) {

                            timeAte.setText(hourOfDay + ":00");
                        }

                        else {

                            timeAte.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, currentHour, currentMinute, true);

                customTimePickerDialog.show();
            }
        });

        // 식단 추가 버튼 클릭
        addDietMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (foodName.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "음식명을 입력하세요.", Toast.LENGTH_SHORT).show();
                    foodName.requestFocus();
                    return;
                }

                if (servings.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "수량을 입력하세요.", Toast.LENGTH_SHORT).show();
                    servings.requestFocus();
                    return;
                }

                if (timeAte.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "시간을 입력하세요.", Toast.LENGTH_SHORT).show();
                    timeAte.requestFocus();
                    return;
                }

                strFoodName = foodName.getText().toString();
                douServings = Double.parseDouble(servings.getText().toString());
                strTimeAte = timeAte.getText().toString();

                int cnt = 0;
                for (int i = 0; i < tmpArrayList.size(); i++) {

                    if (strFoodName.equals(tmpArrayList.get(i).getFoodName())) {

                        cnt = 1;
                        intFoodID = tmpArrayList.get(i).getFoodID();
                        douCalorie = tmpArrayList.get(i).getCaloridPerAmount();
                        douProtein = tmpArrayList.get(i).getProteinPerAmount();
                        douFat = tmpArrayList.get(i).getFatPerAmount();
                        douCarbohydrate = tmpArrayList.get(i).getCarbohydratePerAmount();
                        break;
                    }
                }

                if (cnt == 0) {

                    foodName.setText("");
                    Toast.makeText(getActivity(), "존재하지 않는 음식명입니다.", Toast.LENGTH_SHORT).show();
                    foodName.requestFocus();
                    return;
                }

                listViewAdapterDietMenuRegister.addDietMenu(intFoodID, strFoodName, douServings, douCalorie, douProtein, douFat, douCarbohydrate, strTimeAte);

                foodName.setText("");
                servings.setText("");
                timeAte.setText("");

                listViewAdapterDietMenuRegister.notifyDataSetChanged();
            }
        });

        if (viewModelFactory == null) {

            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance( getActivity().getApplication());
        }

        dietMenuViewModel = new ViewModelProvider(this, viewModelFactory).get(DietMenuViewModel.class);

        // 현재 날짜 받아오기
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        final String year = String.valueOf(Integer.parseInt(yearFormat.format(currentTime)));
        final String month = String.valueOf(Integer.parseInt(monthFormat.format(currentTime)));
        final String day = String.valueOf(Integer.parseInt(dayFormat.format(currentTime)));

        final String currentDate = year + "." + month + "." + day;

        cancelDietMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("currentDate", currentDate);
                dietMenuInquiryFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().remove(DietMenuRegisterFragment.this).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,dietMenuInquiryFragment).commitAllowingStateLoss();
            }
        });

        // 식단 등록
        registerDietMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<DietMenu> dietMenuArrayList = new ArrayList<DietMenu>();
                dietMenuArrayList.addAll(listViewAdapterDietMenuRegister.getDietMenuArrayList());

                for (int i = 0; i < dietMenuArrayList.size(); i++) {

                    String foodName = dietMenuArrayList.get(i).getFoodName();  // 음식 ID
                    double intakeAmount = dietMenuArrayList.get(i).getServings();   // 수량 (섭취량)
                    double totalCalorie = Math.round(dietMenuArrayList.get(i).getCalorie() * intakeAmount * 100) / 100;     // 음식 칼로리 * 수량 = 총 칼로리
                    double totalProtein = Math.round(dietMenuArrayList.get(i).getProtein() * intakeAmount * 100) / 100;     // 총 단백질
                    double totalFat = Math.round(dietMenuArrayList.get(i).getFat() * intakeAmount * 100) / 100;     // 총 지방
                    double totalCarbohydrate = Math.round(dietMenuArrayList.get(i).getCarbohydrate() * intakeAmount * 100) / 100;     // 총 탄수화물
                    String intakeDate = year + "." + month + "." + day;
                    String intakeTime = dietMenuArrayList.get(i).getTimeAte();

                    String[] hour = intakeTime.split(":");
                    if ((Integer.parseInt(hour[0]) / 10) == 0) {

                        intakeTime = "0" + dietMenuArrayList.get(i).getTimeAte();
                    }

                    com.example.sensitive_coach.Room.Entity.DietMenu dietMenu = new com.example.sensitive_coach.Room.Entity.DietMenu(foodName, intakeAmount, totalCalorie, totalProtein, totalFat, totalCarbohydrate, intakeDate, intakeTime);

                    dietMenuViewModel.insertDietMenu(dietMenu);
                }

                dietMenuArrayList.clear();
                listViewAdapterDietMenuRegister.removeDietMenu();
                listViewAdapterDietMenuRegister.notifyDataSetChanged();

                Bundle bundle = new Bundle();
                bundle.putString("currentDate", currentDate);
                dietMenuInquiryFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().remove(DietMenuRegisterFragment.this).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,dietMenuInquiryFragment).commitAllowingStateLoss();
            }
        });

        return viewGroup;
    }

    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        foodArrayList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {

            foodArrayList.addAll(tmpArrayList);
        }

        // 문자 입력을 할때..
        else {

            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0; i < tmpArrayList.size(); i++) {

                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (tmpArrayList.get(i).getFoodName().toLowerCase().contains(charText)) {

                    // 검색된 데이터를 리스트에 추가한다.
                    foodArrayList.add(tmpArrayList.get(i));
                }
            }
        }

        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        listViewAdapterDietMenuFoodSearch.notifyDataSetChanged();
    }
}
