package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kututistesis.R;
import com.example.kututistesis.util.Util;

public class MainActivity extends AppCompatActivity {

    private TextView textSignUp;
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_inicio_sesion);

        // Inicializa los elementos de la vista
        textSignUp = (TextView) findViewById(R.id.text_sign_up);
        buttonSignIn = (Button) findViewById(R.id.button_sign_in);
        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);

        // Subraya el texto para ir a la vista de registro
        textSignUp.setPaintFlags(textSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Eventos de la vista
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPaginaPrincipal();
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistro();
            }
        });
    }

    private void goToRegistro() {
        Intent intent = new Intent(this, Registro1Activity.class);
        startActivity(intent);
    }

    private void goToPaginaPrincipal() {
        if(isValid()) {
            Intent intent = new Intent(this, PaginaPrincipalActivity.class);
            startActivity(intent);
        }
    }

    private boolean isValid() {
        String email = editTextEmail.getText().toString().trim();
        return (email.length() > 0) &&
                (editTextPassword.getText().toString().trim().length() >= 8) &&
                (editTextPassword.getText().toString().trim().length() <= 16) &&
                Util.isEmailValid(email);
    }

}
