package com.example.sensitive_coach.UIController;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.sensitive_coach.R;
import com.example.sensitive_coach.Room.Entity.Routine;
import com.example.sensitive_coach.ViewModel.RoutineViewModel;

import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelStore viewModelStore = new ViewModelStore();

    private RoutineViewModel routineViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_database_test);

        if (viewModelFactory == null) {

            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        }

        routineViewModel = new ViewModelProvider(this, viewModelFactory).get(RoutineViewModel.class);

        routineViewModel.inquiryRoutines().observe(this, new Observer<List<Routine>>() {

            @Override
            public void onChanged(List<Routine> routines) {

                ListView listView = findViewById(R.id.listView);
                ListAdapter listAdapter = listView.getAdapter();

                if (listAdapter instanceof ArrayAdapter) {

                    ArrayAdapter<Routine> routineArrayAdapter = (ArrayAdapter<Routine>) listAdapter;
                    routineArrayAdapter.clear();

                    if (routines != null) {

                        routineArrayAdapter.addAll(routines);
                    }

                    routineArrayAdapter.notifyDataSetChanged();
                }

                else {

                    listAdapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, routines);
                    listView.setAdapter(listAdapter);
                }
            }
        });

        ListView listView = findViewById(R.id.listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Routine) {
                    routineViewModel.deleteRoutine(((Routine) item).getRoutineID());
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.actionAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputRoutineName = findViewById(R.id.inputRoutineName);
                if (inputRoutineName.getText().toString().trim().length() == 0)
                    return;

                EditText inputExerciseID = findViewById(R.id.inputExerciseID);
                if (inputExerciseID.getText().toString().trim().length() == 0)
                    return;

                try {
                    int exerciseID = Integer.parseInt(inputExerciseID.getText().toString());
                    String routineName = inputRoutineName.getText().toString();

                    Routine routine = new Routine();
                    routine.setRoutineName(routineName);
                    routine.setExerciseID(exerciseID);

                    routineViewModel.insertRoutine(routine);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        viewModelStore.clear();
        super.onDestroy();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {

        return viewModelStore;
    }
}
