package com.example.sensitive_coach;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.sensitive_coach.CSVFile.CSVFileReader;

import java.io.InputStream;
import java.util.List;

public class CSVFileTest extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        InputStream inputStream = getResources().openRawResource(R.raw.food_data);
        CSVFileReader csvFileReader = new CSVFileReader(inputStream);
        List foodDataList = csvFileReader.read();

        for (int i = 0; i < foodDataList.size(); i++) {

            Object[] foodData = (Object[]) foodDataList.get(i);
        }
    }
}
