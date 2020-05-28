package com.example.kututistesis.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kututistesis.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class SesionConsonanticoGrabarActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private Button mBotonHablar;
    private String lista_palabras;
    private String lista_confianza;

    private ImageButton hablarAhoraBoton;
    private ImageView imageViewConsonanticosAtras;
    private TextView palabra;
    TTSManager ttsManager = null;

    private ImageView imageViewPalabraVocabulario;

    String url;

    String texto_palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sesion_consonantico_grabar);

        imageViewPalabraVocabulario = findViewById(R.id.imageViewPalabraVocabulario);

        Intent intent = getIntent();

        url = intent.getStringExtra("imagen_palabra");

        Picasso.get().load(url).into(imageViewPalabraVocabulario);

        mBotonHablar = findViewById(R.id.buttonMicrofono);
        lista_palabras = "";
        lista_confianza = "";

        ttsManager = new TTSManager();
        ttsManager.init(this);
        palabra = findViewById(R.id.textPalabra);

        texto_palabra = intent.getStringExtra("texto_palabra");

        palabra.setText(texto_palabra);

        hablarAhoraBoton = findViewById(R.id.imageAltavoz);
        imageViewConsonanticosAtras = (ImageView) findViewById(R.id.imageViewConsonanticosAtras);


        imageViewConsonanticosAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SesionConsonanticoGrabarActivity.super.onBackPressed();
            }
        });

        hablarAhoraBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = palabra.getText().toString();
                ttsManager.initQueue(texto);
            }
        });
        mBotonHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });
    }

    private void iniciarEntradaVoz() {

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hola dime lo que sea");
        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    float[]confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

                    for (int i = 0; i < result.size();i++)
                    {
                        lista_palabras = lista_palabras + result.get(i) +"\n";
                    }
                    lista_confianza = Float.toString(confidence[0]);
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
}
