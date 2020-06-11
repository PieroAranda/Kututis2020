package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kututistesis.R;

public class ResultadosActivity extends AppCompatActivity {

    private ImageView imageViewAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_resultados);

        imageViewAtras = (ImageView) findViewById(R.id.imageViewResultadosAtras);
        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
    }

    private void goToMenu() {
        onBackPressed();
    }
}
