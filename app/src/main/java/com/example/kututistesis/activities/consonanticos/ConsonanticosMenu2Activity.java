package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.VocabularioAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Vocabulario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsonanticosMenu2Activity extends AppCompatActivity implements VocabularioAdapter.OnVocabularioListener{

    private ApiClient apiClient;
    private String url;

    private RecyclerView recyclerView;
    private VocabularioAdapter vocabularioAdapter;
    private ProgressBar progressBarMenu;

    private List<Vocabulario> vocabularioList;

    private ImageView imageViewAtras;

    private Integer fonema_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consonanticos_menu_2);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        apiClient = ApiClient.getInstance();
        url = "http://192.168.0.7:82/curso-laravel/kututis/storage/app/images/";
        recyclerView = findViewById(R.id.recyclerVocabulario);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageViewAtras = findViewById(R.id.imageViewVocabularioAtras);
        progressBarMenu = findViewById(R.id.progressBarConsonanticosMenu2);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsonanticosMenu2Activity.super.onBackPressed();
            }
        });

        vocabularioAdapter = new VocabularioAdapter();

        Intent intent = getIntent();

        fonema_id = intent.getIntExtra("consonante_id",0);

        obtenerVocabulario(fonema_id);
    }

    public void obtenerVocabulario(Integer fonema_id){
        progressBarMenu.setVisibility(View.VISIBLE);

        apiClient.buscarvocabularioxfonemaid(fonema_id).enqueue(new Callback<List<Vocabulario>>() {
            @Override
            public void onResponse(Call<List<Vocabulario>> call, Response<List<Vocabulario>> response) {
                progressBarMenu.setVisibility(View.GONE);

                vocabularioList = response.body();
                for(Vocabulario vocabulario:vocabularioList)
                {
                    url = url + vocabulario.getImagen();
                    vocabulario.setImagen(url);
                    url = "http://192.168.0.7:82/curso-laravel/kututis/storage/app/images/";
                }
                vocabularioAdapter.setData(vocabularioList, ConsonanticosMenu2Activity.this);
                recyclerView.setAdapter(vocabularioAdapter);
            }

            @Override
            public void onFailure(Call<List<Vocabulario>> call, Throwable t) {
                progressBarMenu.setVisibility(View.GONE);

                Log.e("Obteniendo vocabulario", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onVocabularioClick(int position) {
        String imagen = vocabularioList.get(position).getImagen();
        String palabra = vocabularioList.get(position).getPalabra();
        Intent intent = new Intent(this, ConsonanticosSesionActivity.class);
        intent.putExtra("imagen_palabra", imagen);
        intent.putExtra("texto_palabra", palabra);
        intent.putExtra("consonante_id", fonema_id);
        startActivity(intent);
    }
}
