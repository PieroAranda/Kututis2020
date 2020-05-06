package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import android.widget.VideoView;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Praxias;
import com.example.kututistesis.model.SesionPraxia;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PraxiasActivity extends AppCompatActivity {
    private ApiClient apiClient;
    private String url;

    Toolbar toolbar;
    RecyclerView recyclerView;
    PraxiasAdapter praxiasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praxias);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);

        apiClient = ApiClient.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        praxiasAdapter = new PraxiasAdapter();


        url = "http://10.0.2.2:82/curso-laravel/kututis/storage/app/images/";
        obtenerPraxias();
    }

    public void obtenerPraxias() {
        apiClient.listarpraxias().enqueue(new Callback<List<Praxias>>() {
            @Override
            public void onResponse(Call<List<Praxias>> call, Response<List<Praxias>> response) {
                if(response.isSuccessful()){
                    List<Praxias> praxiasList = response.body();
                    for(Praxias prax : praxiasList)
                    {
                        String urlImagen = url + prax.getVideo();
                        prax.setVideo(urlImagen);
                    }
                    praxiasAdapter.setData(praxiasList);
                    recyclerView.setAdapter(praxiasAdapter);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema, no se pudo obtener el video",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<Praxias>> call, Throwable t) {
                Log.e("Obteniendo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void goToSesionPraxiaGrabar(View view) {
        Intent intent = new Intent(this, SesionPraxiaGrabarActivity.class);
        startActivity(intent);
    }



}
