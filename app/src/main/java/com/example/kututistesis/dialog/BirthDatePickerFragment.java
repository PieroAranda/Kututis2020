package com.example.kututistesis.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class BirthDatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    public static BirthDatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        BirthDatePickerFragment fragment = new BirthDatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -2);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        return dialog;
    }

}