package com.example.kututistesis.util;

import android.util.Log;

import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;

public class Validations {

    public static SimpleCustomValidation notBlank = new SimpleCustomValidation() {
        @Override
        public boolean compare(String s) {
            Log.i("SIGNUP", s + " " + s.trim().length());
            return s.trim().length() > 0;
        }
    };
}
