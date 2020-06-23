package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

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
    private int consonanteId;
    String url;
    String textoPalabra;

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
                goToConsonanticosMenu();
            }
        });

        imageViewRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonanticosSesion();
            }
        });

        Intent intent = getIntent();
        this.puntaje = intent.getIntExtra("puntaje", 0);
        consonanteId = intent.getIntExtra("consonante_id", -1);
        textoPalabra = intent.getStringExtra("texto_palabra");
        url = intent.getStringExtra("imagen_palabra");

        showPuntaje();
    }

    private void goToConsonanticosSesion() {
        finish();
        Intent intent = new Intent(this, ConsonanticosSesionActivity.class);
        intent.putExtra("imagen_palabra", url);
        intent.putExtra("texto_palabra", textoPalabra);
        intent.putExtra("consonante_id", consonanteId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showPuntaje() {
        imageViewContenedor.setImageResource((puntaje == 10) ? R.drawable.fondo_felicitaciones : R.drawable.fondo_sigue_intentando);
        textViewPuntaje.setText(String.valueOf(puntaje) + " / 10");
    }

    private void goToConsonanticosMenu() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("consonante_id", consonanteId);
        NavUtils.navigateUpTo(this, intent);
    }
}
