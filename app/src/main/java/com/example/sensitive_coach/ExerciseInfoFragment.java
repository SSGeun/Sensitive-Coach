package com.example.sensitive_coach;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sensitive_coach.Model.Exercise;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ExerciseInfoFragment extends Fragment {

    private String exerciseClassification;
    private String exerciseName;
    private String exerciseVideopath;
    private String exerciseNumber;

    private TextView label;
    private TextView classInfo;
    private ImageView Infoimg;

    private TextView timeInfo;
    private Button start;

    private String times;
    private String sets;

    private TrainingMainFragment trainingMainFragment;

    private String tmp;

    private String squat;

    private ArrayList<Exercise> list = new ArrayList<Exercise>();
    public static ExerciseInfoFragment newInstance() {
        return new ExerciseInfoFragment();
    }

    ViewGroup viewGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.exercise_info_fragment, container, false);

        label =(TextView)viewGroup.findViewById(R.id.label);
        classInfo = (TextView) viewGroup.findViewById(R.id.classInfo);
        Infoimg = (ImageView) viewGroup.findViewById(R.id.Infoimg);

        timeInfo = (TextView) viewGroup.findViewById(R.id.timeInfo);
        start = (Button) viewGroup.findViewById(R.id.start);

        times = String.valueOf(5);
        sets = String.valueOf(2);
        tmp = times + "회 / " + sets + "세트";

        timeInfo.setText(tmp);

        exerciseClassification = getArguments().getString("exerciseClassification");
        exerciseName = getArguments().getString("exerciseName");
        exerciseVideopath = getArguments().getString("exerciseVideopath");
        exerciseNumber = getArguments().getString("exerciseNumber");
        squat = getArguments().getString("position");

        if (squat.equals("0")) {
            classInfo.setText("스쿼트");

            label.setText(exerciseClassification);
            Infoimg.setImageResource(R.drawable.squat_main);
            timeInfo.setText(tmp);
        }
        else {

            classInfo.setText(exerciseName);

            label.setText(exerciseClassification);
            Infoimg.setImageResource(R.drawable.squat_main);
            timeInfo.setText(tmp);
        }

        Infoimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

               openBrowser(v);
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

        });

        trainingMainFragment = new TrainingMainFragment();
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("times", times);
                bundle.putString("sets", sets);
                trainingMainFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().remove(ExerciseInfoFragment.this).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, trainingMainFragment).commitAllowingStateLoss();
            }
        });


        return viewGroup;
    }
}