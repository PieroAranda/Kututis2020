package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.consonanticos.MenuConsonanticosActivity;
import com.example.kututistesis.activities.praxias.MenuPraxiasActivity;
import com.example.kututistesis.activities.vocalicos.MenuVocalicosActivity;

public class MenuPrincipalActivity extends AppCompatActivity {

    private ImageView imageViewPraxias;
    private ImageView imageViewConsonatesVocalicos;
    private ImageView imageViewConsonatesConsonanticos;
    private ImageView imageViewLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.menu_principal_activity);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        // Inicializa los elementos de la vista
        imageViewPraxias = findViewById(R.id.button_praxias);
        imageViewConsonatesVocalicos = findViewById(R.id.button_fonemas_vocalicos);
        imageViewConsonatesConsonanticos = findViewById(R.id.button_fonemas_consonanticos);
        imageViewLogout = findViewById(R.id.image_logout);

        // Eventos de la vista
        imageViewPraxias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPraxias();
            }
        });

        imageViewConsonatesVocalicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonatesVocalicos();
            }
        });

        imageViewConsonatesConsonanticos.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(this, InicioSesionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToConsonatesConsonanticos() {
        Intent intent = new Intent(this, MenuConsonanticosActivity.class);
        startActivity(intent);
    }

    private void goToConsonatesVocalicos() {
        Intent intent = new Intent(this, MenuVocalicosActivity.class);
        startActivity(intent);
    }

    private void goToPraxias() {
        Intent intent = new Intent(this, MenuPraxiasActivity.class);
        startActivity(intent);
    }
}
