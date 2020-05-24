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
    private Boolean isRegister;

    public static BirthDatePickerFragment newInstance(Boolean isRegister,DatePickerDialog.OnDateSetListener listener) {
        BirthDatePickerFragment fragment = new BirthDatePickerFragment();
        fragment.setListener(isRegister,listener);
        return fragment;
    }

    public void setListener(Boolean isRegister,DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
        this.isRegister = isRegister;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        if(isRegister){
            c.add(Calendar.YEAR, -2);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        return dialog;
    }

}