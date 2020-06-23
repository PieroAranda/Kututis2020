package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.ConsonantesAdapter;
import com.example.kututistesis.adapters.MenuBanderaAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Banderin;
import com.example.kututistesis.model.Fonema;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsonanticosMenuActivity extends AppCompatActivity implements ConsonantesAdapter.OnConsonantesListener {

    private RecyclerView recyclerViewFonemaConsonantico;
    private ImageView imageViewAtras;
    private ApiClient apiClient;
    private List<Fonema> fonemas;
    private MenuBanderaAdapter consonantesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consonanticos_menu);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        imageViewAtras = findViewById(R.id.imageViewConsonanticosAtras);
        recyclerViewFonemaConsonantico = findViewById(R.id.recyclerFonemasConsonanticos);

        apiClient = ApiClient.getInstance();
        consonantesAdapter = new MenuBanderaAdapter();

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsonanticosMenuActivity.super.onBackPressed();
            }
        });

        loadFonemas();
    }

    public void loadFonemas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                ConsonanticosMenuActivity.this, LinearLayoutManager.VERTICAL, false
        );
        recyclerViewFonemaConsonantico.setLayoutManager(layoutManager);
        recyclerViewFonemaConsonantico.setItemAnimator(new DefaultItemAnimator());

        apiClient.getFonemas().enqueue(new Callback<List<Fonema>>() {
            @Override
            public void onResponse(Call<List<Fonema>> call, Response<List<Fonema>> response) {
                fonemas = response.body();
                List<Banderin> banderines = new ArrayList<>();
                for (Fonema f : fonemas) {
                    banderines.add(new Banderin(f.getFonema().trim()));
                }
                consonantesAdapter.setData(banderines, ConsonanticosMenuActivity.this);
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
        Intent intent = new Intent(this, ConsonanticosMenu2Activity.class);
        intent.putExtra("consonante_id", consonante_id);
        startActivity(intent);
    }
}
