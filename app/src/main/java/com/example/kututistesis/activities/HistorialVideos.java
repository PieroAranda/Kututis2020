package com.example.kututistesis.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.SesionPraxia;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialVideos extends AppCompatActivity {

    private ApiClient apiClient;
    private String url;
    private File fileVideo;
    private VideoView video;
    private ImageButton play;
    private ProgressBar progressBarVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_videos);

        apiClient = ApiClient.getInstance();

        video = (VideoView) findViewById(R.id.videoViewCargar);
        play = (ImageButton) findViewById(R.id.btnPlay);
        progressBarVideos = (ProgressBar) findViewById(R.id.progress_videos);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
        url = "http://192.168.1.13:82/curso-laravel/kututis/";
        obtenerVideos();
    }


    public void obtenerVideos() {
        apiClient.listar_sesionpraxias().enqueue(new Callback<List<SesionPraxia>>() {
            @Override
            public void onResponse(Call<List<SesionPraxia>> call, Response<List<SesionPraxia>> response) {

                if(response.isSuccessful()){
                    List<SesionPraxia> sesionPraxiaList = response.body();
                    url = url + sesionPraxiaList.get(sesionPraxiaList.size()-1).getRuta_servidor();
                    play.setVisibility(View.VISIBLE);
                    progressBarVideos.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema, no se pudo obtener el video",
                            Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<List<SesionPraxia>> call, Throwable t) {
                Log.e("Obteniendo videos", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void playVideo() {
        try {
            if (!video.isPlaying()){
                Uri uri = Uri.parse(url);
                video.setVideoURI(uri);

                MediaController mediaController = new MediaController(this);
                video.setMediaController(mediaController);
                mediaController.setAnchorView(video);

                video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        play.setImageResource(R.drawable.play);
                    }
                });
            }else {
                video.pause();
                play.setImageResource(R.drawable.play);
            }

        }catch (Exception e)
        {

        }
        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                video.start();
                play.setImageResource(R.drawable.pausa);
            }
        });
    }
}
