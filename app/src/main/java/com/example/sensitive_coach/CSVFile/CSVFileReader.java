package com.example.sensitive_coach.CSVFile;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// CSV 파일 읽어오기
public class CSVFileReader {

    InputStream inputStream;

    public CSVFileReader(InputStream inputStream) {

        this.inputStream = inputStream;
    }

    // CSV 파일 한 줄 씩 읽어서 List 배열에 저장
    public List read() {

        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {

            String csvLine;

            while((csvLine = reader.readLine()) != null) {

                String[] row = csvLine.split(",");

                resultList.add(row);
            }
        } catch (IOException ioException) {

            throw new RuntimeException("Error is reading CSV file: " + ioException);
        } finally {

            try {

                inputStream.close();
            } catch(IOException ioException) {

                throw new RuntimeException("Error while closing input stream: " + ioException);
            }
        }

        return resultList;
    }
}
