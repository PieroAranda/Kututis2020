package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kututistesis.R;
import com.example.kututistesis.dialog.BirthDatePickerFragment;

public class Registro1Activity extends AppCompatActivity {

    private Button buttonNext;
    private EditText editTextBirthDate;
    private EditText editTextNames;
    private EditText editTextLastname;
    private EditText editTextMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar);

        // Inicializa los elementos de la vista
        editTextNames = (EditText) findViewById(R.id.edit_text_names);
        editTextLastname = (EditText) findViewById(R.id.edit_text_lastname);
        editTextBirthDate = (EditText) findViewById(R.id.edit_text_birthdate);
        editTextMobileNumber = (EditText) findViewById(R.id.edit_text_mobile_number);
        buttonNext = (Button) findViewById(R.id.button_next);

        // Eventos de la vista
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

        // Abre el dialogo para escoger la fecha nacimiento si se hace tap al botón next del teclado
        editTextLastname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openDialogDatePicker();
                    return true;
                }
                return false;
            }
        });

        editTextMobileNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    goToRegistro2();
                    return true;
                }
                return false;
            }
        });

        // Muestra el teclado para ingresar los nombres
        if(editTextNames.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void openDialogDatePicker() {
        BirthDatePickerFragment newFragment = BirthDatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                editTextBirthDate.setText(selectedDate);

                if(editTextMobileNumber.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        newFragment.show(Registro1Activity.this.getSupportFragmentManager(), "datePicker");
    }

    private void goToRegistro2() {
        if (isValid()) {
            Intent intent = new Intent(this, Registro2Activity.class);
            startActivity(intent);
        }
    }

    private boolean isValid() {
        return (editTextNames.getText().toString().trim().length() > 0) &&
                (editTextLastname.getText().toString().trim().length() > 0) &&
                (editTextBirthDate.getText().toString().trim().length() > 0) &&
                (editTextMobileNumber.getText().toString().trim().length() == 9);
    }
}
