package com.example.kututistesis.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.consonanticos.ConsonanticosMenu2Activity;
import com.example.kututistesis.adapters.LogrosAdapter;
import com.example.kututistesis.adapters.VocabularioAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.PacienteLogro;
import com.example.kututistesis.model.SesionVocabulario;
import com.example.kututistesis.util.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuLogros extends AppCompatActivity implements LogrosAdapter.OnLogrosListener {

    private ApiClient apiClient;
    private String url;

    private RecyclerView recyclerView;
    private LogrosAdapter logrosAdapter;
    private ProgressBar progressBarMenu;


    private ImageView imageViewAtras;


    private Global global;
    private Integer paciente_id;

    private List<PacienteLogro> pacienteLogroList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu_logros);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        apiClient = ApiClient.getInstance();
        recyclerView = findViewById(R.id.recyclerLogros);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageViewAtras = findViewById(R.id.imageViewLogrosAtras);
        progressBarMenu = findViewById(R.id.progressBarLogrosMenu);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuLogros.super.onBackPressed();
            }
        });

        logrosAdapter = new LogrosAdapter();

        /*Intent intent = getIntent();

        fonema_id = intent.getIntExtra("consonante_id",0);*/
        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();

        obtenerLogrosPaciente(paciente_id);
    }

    public void obtenerLogrosPaciente(Integer paciente_id){
        progressBarMenu.setVisibility(View.VISIBLE);

        apiClient.listarlogroxusuarioid(paciente_id).enqueue(new Callback<List<PacienteLogro>>() {
            @Override
            public void onResponse(Call<List<PacienteLogro>> call, Response<List<PacienteLogro>> response) {
                progressBarMenu.setVisibility(View.GONE);
                pacienteLogroList = response.body();

                logrosAdapter.setData(pacienteLogroList, MenuLogros.this);
                recyclerView.setAdapter(logrosAdapter);
            }

            @Override
            public void onFailure(Call<List<PacienteLogro>> call, Throwable t) {
                Log.d("FalloLogrosPaciente", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener los logros del paciente",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onLogrosClick(int position) {

    }
}
