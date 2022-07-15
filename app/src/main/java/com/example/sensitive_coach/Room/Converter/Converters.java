package com.example.sensitive_coach.Room.Converter;

import androidx.room.TypeConverter;

import java.sql.Date;

// Converter: Room이 유지할 수 있는 유형과 맞춤 클래스를 상호 변환
public class Converters {

    // Date 객체를 Long 개체로 변환
    @TypeConverter
    public static Date fromTimestamp(Long value) {

        // value가 null 이면 null 아니면 date
        return value == null ? null : new Date(value);
    }

    // Long 개체를 Date 개체로 변환
    @TypeConverter
    public static Long dateToTimestamp(Date date) {

        // date가 null이면 null 아니면 date.getTime
        return date == null ? null : date.getTime();
    }
}
