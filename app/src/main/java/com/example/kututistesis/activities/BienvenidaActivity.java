package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.kututistesis.R;

public class BienvenidaActivity extends AppCompatActivity {

    // Tiempo de transición en milisegundos
    private static int TIME_DELAY_MILLISECONDS = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bienvenida);

        // Retrasa la transición por 2.5 segundos
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                goToMenuPrincipal();
            }
        }, TIME_DELAY_MILLISECONDS);
    }

    private void goToMenuPrincipal() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
