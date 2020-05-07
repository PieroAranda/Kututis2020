package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kututistesis.R;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private Button buttonPraxias;
    private Button buttonConsonatesVocalicos;
    private Button buttonConsonatesConsonanticos;
    private ImageView imageViewLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_pantalla_principal);

        // Inicializa los elementos de la vista
        buttonPraxias = (Button) findViewById(R.id.button_praxias);
        buttonConsonatesVocalicos = (Button) findViewById(R.id.button_fonemas_vocalicos);
        buttonConsonatesConsonanticos = (Button) findViewById(R.id.button_fonemas_consonanticos);
        imageViewLogout = (ImageView) findViewById(R.id.image_logout);

        // Eventos de la vista
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

        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToConsonatesConsonanticos() {
        Intent intent = new Intent(this, FonemasConsonanticosActivity.class);
        startActivity(intent);
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
