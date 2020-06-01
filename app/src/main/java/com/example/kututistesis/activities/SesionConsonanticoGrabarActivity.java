package com.example.kututistesis.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView imageViewBarra;

    private TextView texto_contador;

    String url;

    String texto_palabra;

    Double factor_minimo_confianza;

    Integer contador;

    String texto;

    String [] texto_array;

    String palabra_en_pantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sesion_consonantico_grabar);

        factor_minimo_confianza = 0.70;

        imageViewPalabraVocabulario = findViewById(R.id.imageViewPalabraVocabulario);
        imageViewBarra = (ImageView) findViewById(R.id.imageViewConsonanticosBarra);

        Bitmap barraSprites = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_barra);
        Bitmap barra = Bitmap.createBitmap(barraSprites, 0, 0, 1046, barraSprites.getHeight());
        imageViewBarra.setImageBitmap(getResizedBitmap(barra, 400, 100));

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

        palabra_en_pantalla = palabra.getText().toString();

        palabra_en_pantalla = palabra_en_pantalla.toLowerCase();

        texto_contador = findViewById(R.id.textViewContador);

        texto = texto_contador.getText().toString();
        texto_array = texto.split(" ");

        contador = Integer.parseInt(texto_array[0]);

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
                ttsManager.initQueue(palabra_en_pantalla);
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
                        lista_palabras = lista_palabras + result.get(i) +"\n"; // se esta mostrando todos los resultados anteriores no es que no funcione la libreria, se debe borrar la data anterior
                    }
                    lista_confianza = Float.toString(confidence[0]);

                    String lista_palabras_uno = result.get(0);

                    //Al parecer el contains no distingue las mayusculas
                    if (confidence[0]>=factor_minimo_confianza && lista_palabras_uno.equals(palabra_en_pantalla)) //lista_palabras_uno.contains(palabra_en_pantalla)
                    {
                        contador++;
                        texto_array[0] = contador.toString();
                        //texto = String.valueOf(texto_array);
                        texto = texto_array[0]+" "+"/"+" "+texto_array[texto_array.length-1];

                        texto_contador.setText(texto);

                        updateBarra(contador);

                        Toast.makeText(getApplicationContext(),lista_palabras,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Intentalo nuevamente...no te rindas",
                                Toast.LENGTH_SHORT)
                                .show();

                        Toast.makeText(getApplicationContext(),lista_palabras,
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                }
                break;
            }
        }
    }

    private void updateBarra(Integer contador) {
        Bitmap barraSprites = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_barra);
        Bitmap barra = Bitmap.createBitmap(barraSprites, 1046 * contador, 0, 1046, barraSprites.getHeight());
        imageViewBarra.setImageBitmap(getResizedBitmap(barra, 400, 100));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
