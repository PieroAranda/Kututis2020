package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kututistesis.R;
import com.example.kututistesis.model.SignUpForm;

public class Registro3Activity extends AppCompatActivity {

    private static final String INTENT_EXTRA_SIGN_UP_DATA = "SIGN_UP_DATA";

    private EditText editTextPetName;
    private Button buttonRegister;
    private SignUpForm signUpForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar_3);

        getIntentData();

        editTextPetName = (EditText) findViewById(R.id.edit_text_pet_name);
        buttonRegister = (Button) findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void getIntentData() {
        if(getIntent() != null){
            signUpForm = (SignUpForm) getIntent().getSerializableExtra(INTENT_EXTRA_SIGN_UP_DATA);
        }
    }

    private void registerUser() {
        if(isValid()) {
            // Se valida y se registra al usuario
            Log.i("SIGNUP", signUpForm.toString());
        }
    }

    private boolean isValid() {
        return editTextPetName.getText().toString().trim().length() > 0;
    }
}
