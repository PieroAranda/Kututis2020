package com.example.kututistesis.activities.praxias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.ConsonantesAdapter;
import com.example.kututistesis.adapters.MenuBanderaAdapter;
import com.example.kututistesis.adapters.PraxiasAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Banderin;
import com.example.kututistesis.model.Praxia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPraxiasActivity extends AppCompatActivity implements PraxiasAdapter.OnPraxiaListener {

    private ApiClient apiClient;
    private RecyclerView recyclerView;
    private MenuBanderaAdapter praxiasAdapter;
    private List<Praxia> praxiasList;
    private ImageView imageViewAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_praxias);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        recyclerView = findViewById(R.id.recyclerViewPraxias);
        imageViewAtras = findViewById(R.id.imageViewPraxiasAtras);

        apiClient = ApiClient.getInstance();

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPraxiasActivity.super.onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        praxiasAdapter = new MenuBanderaAdapter();

        loadPraxias();
    }

    public void loadPraxias() {
        apiClient.listarpraxias().enqueue(new Callback<List<Praxia>>() {
            @Override
            public void onResponse(Call<List<Praxia>> call, Response<List<Praxia>> response) {
                if (response.isSuccessful()) {
                    praxiasList = response.body();
                    List<Banderin> banderines = new ArrayList<>();

                    for (Praxia prax : praxiasList) {
                        String urlVideo = ApiClient.BASE_STORAGE_IMAGE_URL + prax.getVideo();
                        prax.setVideo(urlVideo);
                        String urlImagen = ApiClient.BASE_STORAGE_IMAGE_URL + prax.getImagen();
                        prax.setImagen(urlImagen);
                        banderines.add(new Banderin(prax.getNombre()));
                    }

                    praxiasAdapter.setData(banderines, new ConsonantesAdapter.OnConsonantesListener() {
                        @Override
                        public void onConsonanteClick(int position) {
                            onPraxiaClick(position);
                        }
                    });
                    recyclerView.setAdapter(praxiasAdapter);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema, no se pudo obtener el video",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<Praxia>> call, Throwable t) {
                Log.e("Obteniendo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onPraxiaClick(int position) {
        Integer praxia_id = praxiasList.get(position).getId();
        String video_por_praxia = praxiasList.get(position).getVideo();
        Intent intent = new Intent(this, SesionPraxiasActivity.class);
        intent.putExtra("praxia_id", praxia_id);
        intent.putExtra("video_por_praxia", video_por_praxia);
        startActivity(intent);
    }
}
