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
import com.example.kututistesis.model.SesionVocabulario;
import com.example.kututistesis.model.Vocabulario;
import com.example.kututistesis.util.Global;

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


    private ImageView imageViewAtras;

    private List<SesionVocabulario> sesionVocabulario;


    private Global global;
    private Integer paciente_id;

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
        url = ApiClient.BASE_STORAGE_IMAGE_URL;
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

        /*Intent intent = getIntent();

        fonema_id = intent.getIntExtra("consonante_id",0);*/
        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();

        obtenerVocabulario(paciente_id);
    }

    public void obtenerVocabulario(Integer paciente_id){
        progressBarMenu.setVisibility(View.VISIBLE);

        apiClient.listar_sesionvocabularioxusuario(paciente_id).enqueue(new Callback<List<SesionVocabulario>>() {
            @Override
            public void onResponse(Call<List<SesionVocabulario>> call, Response<List<SesionVocabulario>> response) {
                progressBarMenu.setVisibility(View.GONE);
                sesionVocabulario = response.body();

                vocabularioAdapter.setData(sesionVocabulario, ConsonanticosMenu2Activity.this);
                recyclerView.setAdapter(vocabularioAdapter);

            }

            @Override
            public void onFailure(Call<List<SesionVocabulario>> call, Throwable t) {
                Log.d("FalloSesionPraxia", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener el vocabulario",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        /*apiClient.buscarvocabularioxfonemaid(fonema_id).enqueue(new Callback<List<Vocabulario>>() {
            @Override
            public void onResponse(Call<List<Vocabulario>> call, Response<List<Vocabulario>> response) {
                progressBarMenu.setVisibility(View.GONE);

                vocabularioList = response.body();
                for(Vocabulario vocabulario:vocabularioList)
                {
                    url = url + vocabulario.getImagen();
                    vocabulario.setImagen(url);
                    url = ApiClient.BASE_STORAGE_IMAGE_URL;
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
        });*/
    }

    @Override
    public void onVocabularioClick(int position) {
        String imagen = sesionVocabulario.get(position).getVocabulario().getImagen();
        String palabra = sesionVocabulario.get(position).getVocabulario().getPalabra();
        Integer id_fonema = sesionVocabulario.get(position).getVocabulario().getFonema_id();
        Intent intent = new Intent(this, ConsonanticosSesionActivity.class);
        intent.putExtra("imagen_palabra", imagen);
        intent.putExtra("texto_palabra", palabra);
        intent.putExtra("consonante_id", id_fonema);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerVocabulario(paciente_id);
    }
}
