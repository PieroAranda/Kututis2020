package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.consonanticos.ConsonanticosMenu2Activity;
import com.example.kututistesis.activities.consonanticos.ConsonanticosMenuActivity;
import com.example.kututistesis.activities.praxias.PraxiasMenuActivity;
import com.example.kututistesis.activities.vocalicos.VocalicosMenuActivity;
import com.example.kututistesis.util.Global;

public class MenuPrincipalActivity extends AppCompatActivity {

    private ImageView imageViewPraxias;
    private ImageView imageViewConsonatesVocalicos;
    private ImageView imageViewConsonatesConsonanticos;
    private ImageView imageViewLogout;
    private Global global;
    private Button botonLogros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu_principal);
        global = (Global) getApplicationContext();

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
        botonLogros = findViewById(R.id.buttonLogros);

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

        botonLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogros();
            }
        });
    }

    private void logout() {
        SharedPreferences.Editor editor = global.sharedPref.edit();
        editor.putInt(getString(R.string.saved_user_id), -1);
        editor.commit();

        Intent intent = new Intent(this, InicioSesionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToConsonatesConsonanticos() {
        Intent intent = new Intent(this, ConsonanticosMenu2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToConsonatesVocalicos() {
        Intent intent = new Intent(this, VocalicosMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToPraxias() {
        Intent intent = new Intent(this, PraxiasMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToLogros() {
        Intent intent = new Intent(this, MenuLogros.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
