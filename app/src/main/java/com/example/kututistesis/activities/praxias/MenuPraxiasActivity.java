package com.example.kututistesis.activities.praxias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.PraxiasAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Praxias;
import com.example.kututistesis.util.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPraxiasActivity extends AppCompatActivity implements PraxiasAdapter.OnPraxiaListener {
    private ApiClient apiClient;
    private String url;

    Toolbar toolbar;
    RecyclerView recyclerView;
    PraxiasAdapter praxiasAdapter;
    private Global global;

    private List<Praxias> praxiasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praxias);

        global = (Global) getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);

        apiClient = ApiClient.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        praxiasAdapter = new PraxiasAdapter();


        url = "http://192.168.0.7:82/curso-laravel/kututis/storage/app/images/";
        obtenerPraxias();
    }

    public void obtenerPraxias() {
        apiClient.listarpraxias().enqueue(new Callback<List<Praxias>>() {
            @Override
            public void onResponse(Call<List<Praxias>> call, Response<List<Praxias>> response) {
                if(response.isSuccessful()){
                    praxiasList = response.body();
                    for(Praxias prax : praxiasList)
                    {
                        String urlVideo = url + prax.getVideo();
                        prax.setVideo(urlVideo);
                        String urlImagen = url + prax.getImagen();
                        prax.setImagen(urlImagen);
                    }
                    praxiasAdapter.setData(praxiasList, global, MenuPraxiasActivity.this);
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
