package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kututistesis.R;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private Button buttonPraxias;
    private Button buttonConsonatesVocalicos;
    private Button buttonConsonatesConsonanticos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_pantalla_principal);

        buttonPraxias = (Button) findViewById(R.id.button_praxias);
        buttonConsonatesVocalicos = (Button) findViewById(R.id.button_fonemas_vocalicos);
        buttonConsonatesConsonanticos = (Button) findViewById(R.id.button_fonemas_consonanticos);

        buttonPraxias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPraxias();
            }
        });

        buttonConsonatesVocalicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonatesVocalicos();
            }
        });

        buttonConsonatesConsonanticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonatesConsonanticos();
            }
        });
    }

    private void goToConsonatesConsonanticos() {
    }

    private void goToConsonatesVocalicos() {
        Intent intent = new Intent(this, FonemasVocalicosActivity.class);
        startActivity(intent);
    }

    private void goToPraxias() {
        Intent intent = new Intent(this, PraxiasActivity.class);
        startActivity(intent);
    }
}
