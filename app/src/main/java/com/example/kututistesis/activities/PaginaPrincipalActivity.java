package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kututistesis.R;

public class PaginaPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_pantalla_principal);
    }
}
