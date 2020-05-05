package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Praxias;
import com.example.kututistesis.model.Vocales;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FonemasVocalicosActivity extends AppCompatActivity {
    private ApiClient apiClient;
    private String url;

    Toolbar toolbar;
    RecyclerView recyclerView;
    VocalAdapter vocalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonemas_vocalicos);

        toolbar = findViewById(R.id.toolbarVocales);
        recyclerView = findViewById(R.id.recyclerViewVocales);

        apiClient = ApiClient.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vocalAdapter = new VocalAdapter();


        url = "http://10.0.2.2:82/curso-laravel/kututis/imagenesTerapia/praxias/";
        obtenerVocales();

    }

    public void obtenerVocales() {
        apiClient.listarvocales().enqueue(new Callback<List<Vocales>>() {
            @Override
            public void onResponse(Call<List<Vocales>> call, Response<List<Vocales>> response) {
                if(response.isSuccessful()){
                    List<Vocales> vocalesList = response.body();
                    vocalAdapter.setData(vocalesList);
                    recyclerView.setAdapter(vocalAdapter);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema, no se pudo obtener el video",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<Vocales>> call, Throwable t) {
                Log.e("Obteniendo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
