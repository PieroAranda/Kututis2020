package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kututistesis.R;

public class ConsonanticosResultadosActivity extends AppCompatActivity {

    private ImageView imageViewAtras;
    private ImageView imageViewRepetir;
    private ImageView imageViewContenedor;
    private TextView textViewPuntaje;
    private int puntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consonanticos_resultados);

        imageViewAtras = findViewById(R.id.imageViewConsonanticosResultadosAtras);
        imageViewRepetir = findViewById(R.id.imageViewConsonanticosResultadosRepetir);
        textViewPuntaje = findViewById(R.id.textViewConsonanticosResultadosPuntaje);
        imageViewContenedor = findViewById(R.id.imageViewConsonanticosResultadosContenedor);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });

        imageViewRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        this.puntaje = intent.getIntExtra("puntaje", 0);
        showPuntaje();
    }

    private void showPuntaje() {
        imageViewContenedor.setImageResource((puntaje == 10) ? R.drawable.fondo_felicitaciones : R.drawable.fondo_sigue_intentando);
        textViewPuntaje.setText(String.valueOf(puntaje) + " / 10");
    }

    private void goToMenu() {
        onBackPressed();
    }
}
