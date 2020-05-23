package com.example.kututistesis.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialAudios extends AppCompatActivity implements View.OnClickListener{

    private ApiClient apiClient;
    private String url;
    private File fileVideo;
    private Button play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_audios);

        apiClient = ApiClient.getInstance();

        play = findViewById(R.id.btn_play2);
        play.setOnClickListener(this);
        url = "http://10.0.0.2:82/curso-laravel/kututis/";
        obtenerAudios();
    }

    public void obtenerAudios() {
        apiClient.listar_sesionvocales().enqueue(new Callback<List<SesionVocal>>() {
            @Override
            public void onResponse(Call<List<SesionVocal>> call, Response<List<SesionVocal>> response) {

                if(response.isSuccessful()){
                    List<SesionVocal> sesionVocalesList = response.body();
                    url = url + sesionVocalesList.get(sesionVocalesList.size()-1).getRuta_servidor();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema, no se pudo obtener el video",
                            Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<List<SesionVocal>> call, Throwable t) {
                Log.e("Obteniendo videos", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        }catch (IOException e){
        }

        mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproducir audio "+url, Toast.LENGTH_SHORT).show();

    }
}
