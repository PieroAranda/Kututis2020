package com.example.kututistesis.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Fonema;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FonemasConsonanticosActivity extends AppCompatActivity implements ConsonantesAdapter.OnConsonantesListener{

    private RecyclerView recyclerFonemaConsonantico;

    private ApiClient apiClient;

    private List<Fonema> fonemaList;

    private ConsonantesAdapter consonantesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fonemas_consonanticos);

        apiClient = ApiClient.getInstance();

        consonantesAdapter = new ConsonantesAdapter();

        recyclerFonemaConsonantico = findViewById(R.id.recyclerFonemasConsonanticos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                FonemasConsonanticosActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerFonemaConsonantico.setLayoutManager(layoutManager);
        recyclerFonemaConsonantico.setItemAnimator(new DefaultItemAnimator());

        obtenerFonemas();
    }

    public void obtenerFonemas(){
        apiClient.listarfonemas().enqueue(new Callback<List<Fonema>>() {
            @Override
            public void onResponse(Call<List<Fonema>> call, Response<List<Fonema>> response) {
                fonemaList = response.body();
                consonantesAdapter.setData(fonemaList, FonemasConsonanticosActivity.this);
                recyclerFonemaConsonantico.setAdapter(consonantesAdapter);
            }

            @Override
            public void onFailure(Call<List<Fonema>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onConsonanteClick(int position) {
        Integer consonante_id = fonemaList.get(position).getId();
        Intent intent = new Intent(this, VocabularioActivity.class);
        intent.putExtra("consonante_id",consonante_id);
        startActivity(intent);

    }
}
