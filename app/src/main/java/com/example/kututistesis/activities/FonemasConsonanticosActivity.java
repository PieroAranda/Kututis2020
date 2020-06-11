package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Fonema;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FonemasConsonanticosActivity extends AppCompatActivity implements ConsonantesAdapter.OnConsonantesListener {

    private RecyclerView recyclerViewFonemaConsonantico;
    private ImageView imageViewAtras;
    private ApiClient apiClient;
    private List<Fonema> fonemas;
    private ConsonantesAdapter consonantesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fonemas_consonanticos);

        imageViewAtras = findViewById(R.id.imageViewConsonanticosAtras);
        recyclerViewFonemaConsonantico = findViewById(R.id.recyclerFonemasConsonanticos);

        apiClient = ApiClient.getInstance();
        consonantesAdapter = new ConsonantesAdapter();

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FonemasConsonanticosActivity.super.onBackPressed();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                FonemasConsonanticosActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerViewFonemaConsonantico.setLayoutManager(layoutManager);
        recyclerViewFonemaConsonantico.setItemAnimator(new DefaultItemAnimator());

        loadFonemas();
    }

    public void loadFonemas() {
        apiClient.getFonemas().enqueue(new Callback<List<Fonema>>() {
            @Override
            public void onResponse(Call<List<Fonema>> call, Response<List<Fonema>> response) {
                fonemas = response.body();
                consonantesAdapter.setData(fonemas, FonemasConsonanticosActivity.this);
                recyclerViewFonemaConsonantico.setAdapter(consonantesAdapter);
            }

            @Override
            public void onFailure(Call<List<Fonema>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onConsonanteClick(int position) {
        Integer consonante_id = fonemas.get(position).getId();
        Intent intent = new Intent(this, VocabularioActivity.class);
        intent.putExtra("consonante_id", consonante_id);
        startActivity(intent);
    }
}
