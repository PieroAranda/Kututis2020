package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.kututistesis.R;
import com.example.kututistesis.dialog.DatePickerFragment;

public class Registro1Activity extends AppCompatActivity {

    private Button buttonNext;
    private EditText editTextBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar);

        buttonNext = (Button) findViewById(R.id.button_next);
        editTextBirthDate = (EditText) findViewById(R.id.edit_text_birthdate);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistro2();
            }
        });
        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDatePicker();
            }
        });
    }

    private void openDialogDatePicker() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                editTextBirthDate.setText(selectedDate);
            }
        });

        newFragment.show(Registro1Activity.this.getSupportFragmentManager(), "datePicker");
    }

    private void goToRegistro2() {
        Intent intent = new Intent(this, Registro2Activity.class);
        startActivity(intent);
    }
}
