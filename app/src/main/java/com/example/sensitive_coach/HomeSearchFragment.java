package com.example.sensitive_coach;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sensitive_coach.Adapter.ListViewAdapter_ExerciseSearch;
import com.example.sensitive_coach.Adapter.SearchAdapter;
import com.example.sensitive_coach.CSVFile.CSVFileReader;
import com.example.sensitive_coach.Model.Exercise;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeSearchFragment extends Fragment {

    private ExerciseInfoFragment exerciseInfoFragment;

    private String exerciseName;
    private String exerciseVideopath;
    private String exerciseClassification;


    private String exerciseNumber;

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 메인 항목 리스트변수
    private ListView searchListView;    // 검색 리스트 변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    private ListViewAdapter_ExerciseSearch listViewAdapterExerciseSearch;

    private ArrayList<Exercise> exercises;
    private ArrayList<Exercise> arraylist;

    ViewGroup viewGroup;

    @Nullable
    public static HomeSearchFragment newInstance() {
        return new HomeSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.exercise_search_fragment, container, false);

        editSearch = (EditText) viewGroup.findViewById(R.id.editSearch);
        listView = (ListView) viewGroup.findViewById(R.id.listView);
        searchListView = (ListView) viewGroup.findViewById(R.id.searchListView);

        // 리스트를 생성한다.
        list = new ArrayList<String>();
        exercises = new ArrayList<Exercise>();

        // 검색에 사용할 데이터을 미리 저장한다.
        exercises = settingList();

        Bundle bundle = new Bundle();
        exerciseInfoFragment = new ExerciseInfoFragment();

        bundle.putString("exerciseClassification", exerciseClassification);
        bundle.putString("exerciseName", exerciseName);
        bundle.putString("exerciseVideopath", exerciseVideopath);
        bundle.putString("exerciseNumber", exerciseNumber);


        exerciseInfoFragment.setArguments(bundle);

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<Exercise>();
        arraylist.addAll(exercises);

        // 리스트에 연동될 아답터를 생성한다.
        //adapter = new SearchAdapter(list, this);

        adapter = new SearchAdapter();
        adapter.addExercise(exercises);
        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ((MainActivity) getActivity()).replaceFragment(ExerciseInfoFragment.newInstance());

                Bundle bundle = new Bundle();
                exerciseInfoFragment = new ExerciseInfoFragment();
                bundle.putString("position", String.valueOf(position));
                exerciseInfoFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().remove(HomeSearchFragment.this).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, exerciseInfoFragment).commitAllowingStateLoss();
            }
        };
        listView.setOnItemClickListener(listener);

        editSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });


        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        listViewAdapterExerciseSearch = new ListViewAdapter_ExerciseSearch(exercises, getActivity());
        searchListView.setAdapter(listViewAdapterExerciseSearch);
        return viewGroup;
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        adapter.removeExercise();
        exercises.clear();


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            exercises.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getExerciseName().toLowerCase().contains(charText)) {
//                    // 검색된 데이터를 리스트에 추가한다.
                    exercises.add(arraylist.get(i));
                }

            }
        }

        adapter.addExercise(exercises);

        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
        //listViewAdapterExerciseSearch.notifyDataSetChanged();
    }


    // 검색에 사용될 데이터를 리스트에 추가한다.
    public ArrayList<Exercise> settingList() {

        ArrayList<Exercise> exerciseArrayList;

        exerciseArrayList = new ArrayList<Exercise>();

//        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.exercisedb);
        CSVFileReader csvFileReader = new CSVFileReader(inputStream);
        List exerciseDB = csvFileReader.read(); // 한줄씩 저장

        for (int i = 0; i < exerciseDB.size(); i++) {

            Object[] exercise = (Object[]) exerciseDB.get(i);



            exerciseClassification = (String) exercise[0];
            exerciseName = (String) exercise[1];
            exerciseVideopath = (String) exercise[2];
            String numberOfSets = (String) exercise[3];
            String numberPerSet = (String) exercise[4];




            exerciseNumber = numberOfSets + "세트 / " + numberPerSet + "회";
            Exercise exercise1 = new Exercise(exerciseClassification, exerciseName, exerciseVideopath, numberOfSets, numberPerSet);
            exerciseArrayList.add(exercise1);

//          list.addAll(exerciseDB);

            // 데이터 들어오는지 테스트용
//            EditText editSearch = (EditText) findViewById(R.id.editSearch);
//            editSearch.setText(exerciseClassification);

            // listView = (ListView) findViewById(R.id.listView);
            // listView.setAdapter((SearchAdapter) exerciseDB);


        }

        // exercises = new ArrayList<Exercise>();
        exercises.addAll(exerciseArrayList);

        return exerciseArrayList;
    }

    public void openBrowser(View view) {

        //Get url from tag
        String url = (String) view.getTag();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        //pass the url to intent data
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }
//    public void sendInfo(Fragment fragment) {
//        Bundle bundle = new Bundle();
////
////        String param1 = "1";
////        bundle.putString("param1", param1);
////        String param2 = "2";
////        bundle.putString("param2", param2);
////        fragment.setArguments(bundle);// Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
//
//        bundle.putString("exerciseName", exerciseName);
//    }
//
}
