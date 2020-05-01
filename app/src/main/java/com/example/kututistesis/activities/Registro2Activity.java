package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.util.Util;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Registro2Activity extends AppCompatActivity {

    private static final String INTENT_EXTRA_SIGN_UP_DATA = "SIGN_UP_DATA";

    private Button buttonNext;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private SignUpForm signUpForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar_2);

        getIntentData();

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

        editTextPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    goToRegistro3();
                    return true;
                }
                return false;
            }
        });

        if(editTextEmail.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // Obtiene datos de la vista Registro 1
    private void getIntentData() {
        if(getIntent() != null) {
            signUpForm = (SignUpForm) getIntent().getSerializableExtra(INTENT_EXTRA_SIGN_UP_DATA);
            //Log.i("SIGNUP", signUpForm.getNombre() + " " + signUpForm.getApellido() + " " + signUpForm.getCelular());
        }
    }

    private void goToRegistro3() {
        if(isValid()) {
            Intent intent = new Intent(this, Registro3Activity.class);

            signUpForm.setCorreo(editTextEmail.getText().toString().trim());
            signUpForm.setContrasenia(editTextPassword1.getText().toString().trim());
            intent.putExtra(INTENT_EXTRA_SIGN_UP_DATA, (Serializable) signUpForm);

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
