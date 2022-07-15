package com.example.sensitive_coach;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class RoutineMainFragment extends Fragment
{
    ViewGroup viewGroup;
    RoutineAdapter adapter = new RoutineAdapter();
    ListView listview ;

    @Nullable
    @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.routine_fragment_main,container,false);
        listview = (ListView) viewGroup.findViewById(R.id.listView2);
        listview.setAdapter(adapter);
        settingList();

        FloatingActionButton fab = viewGroup.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new FABClickListener());

        return viewGroup;
    }

    private void settingList()
    {
        adapter.addItem(1, "상체 전용 루틴", "설명") ;
        // 두 번째 아이템 추가.
        adapter.addItem(2,"하체 전용 루틴", "설명") ;
        // 세 번째 아이템 추가.
        adapter.addItem(3,"아침에 하기 좋은 코어 운동", "설명") ;
    }

    class FABClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).replaceFragment(HomeSearchFragment.newInstance());// FAB Click 이벤트 처리 구간
        }
    }
}
