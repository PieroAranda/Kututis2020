package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kututistesis.R;

public class Registro3Activity extends AppCompatActivity {

    private EditText editTextPetName;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar_3);

        editTextPetName = (EditText) findViewById(R.id.edit_text_pet_name);
        buttonRegister = (Button) findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        if(isValid()) {
            // Se valida y se regsitra al usuario
        }
    }

    private boolean isValid() {
        return editTextPetName.getText().toString().trim().length() > 0;
    }
}
