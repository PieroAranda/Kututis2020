package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kututistesis.R;
import com.example.kututistesis.util.Util;

import java.util.regex.Pattern;

public class Registro2Activity extends AppCompatActivity {

    private Button buttonNext;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar_2);

        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword1 = (EditText) findViewById(R.id.edit_text_password_1);
        editTextPassword2 = (EditText) findViewById(R.id.edit_text_password_2);
        buttonNext = (Button) findViewById(R.id.button_next);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistro3();
            }
        });
    }

    private void goToRegistro3() {
        if(isValid()) {
            Intent intent = new Intent(this, Registro3Activity.class);
            startActivity(intent);
        }
    }

    private boolean isValid() {
        String email = editTextEmail.getText().toString().trim();
        String password1 = editTextPassword1.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();

        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        // En caso se agrege la restricción de que la contraseña contenga una letra en mayúscula
        //Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");


        return (email.length() > 0) &&
                (password1.length() >= 8) &&
                (password1.length() <= 16) &&
                (password1.matches(password2)) &&
                (specialCharPatten.matcher(password1).find()) &&
                (lowerCasePatten.matcher(password1).find()) &&
                (digitCasePatten.matcher(password1).find()) &&
                Util.isEmailValid(email);
    }
}
