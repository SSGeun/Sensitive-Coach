package com.example.sensitive_coach.UIController;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomTimePickerDialog extends TimePickerDialog {

    private final static int TIME_PICKER_INTERVAL = 10;
    private TimePicker timePicker;
    private final OnTimeSetListener onTimeSetListener;

    public CustomTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {

        super(context, TimePickerDialog.THEME_HOLO_DARK, null, hourOfDay, minute / TIME_PICKER_INTERVAL, is24HourView);

        onTimeSetListener = listener;
    }

    @Override
    public void updateTime(int hourOfDay, int minuteOfHour) {

        timePicker.setCurrentHour(hourOfDay);
        timePicker.setCurrentMinute(minuteOfHour / TIME_PICKER_INTERVAL);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {

            case BUTTON_POSITIVE:
                if (onTimeSetListener != null) {

                    onTimeSetListener.onTimeSet(timePicker, timePicker.getCurrentHour(), timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
                }
                break;

            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    @Override
    public void onAttachedToWindow() {

        super.onAttachedToWindow();

        try {

            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
            timePicker = (TimePicker) findViewById(timePickerField.getInt(null));
            Field field = classForid.getField("minute");

            NumberPicker minuteSpinner = (NumberPicker) timePicker.findViewById(field.getInt(null));
            minuteSpinner.setMinValue(0);
            minuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);

            List<String> displayedValues = new ArrayList<>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {

                displayedValues.add(String.format("%02d", i));
            }

            minuteSpinner.setDisplayedValues(displayedValues.toArray(new String[displayedValues.size()]));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
