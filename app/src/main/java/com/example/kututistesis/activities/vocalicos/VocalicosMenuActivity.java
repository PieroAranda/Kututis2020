package com.example.kututistesis.activities.vocalicos;

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
import com.example.kututistesis.adapters.ConsonantesAdapter;
import com.example.kututistesis.adapters.MenuBanderaAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Banderin;
import com.example.kututistesis.model.PacienteLogro;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionFonema;
import com.example.kututistesis.model.Vocal;
import com.example.kututistesis.util.Global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VocalicosMenuActivity extends AppCompatActivity {

    private ApiClient apiClient;
    private RecyclerView recyclerView;
    private MenuBanderaAdapter vocalAdapter;
    private List<SesionFonema> sesionFonemaList;
    private ImageView imageViewAtras;
    private ProgressBar progressBarMenu;

    private Global global;
    private Integer paciente_id;

    private List<PacienteLogro> pacienteLogroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vocalicos_menu);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        apiClient = ApiClient.getInstance();
        vocalAdapter = new MenuBanderaAdapter();

        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();

        recyclerView = findViewById(R.id.recyclerViewVocalicos);
        imageViewAtras = findViewById(R.id.imageViewVocalicossAtras);
        progressBarMenu = findViewById(R.id.progressBarVocalicosMenu);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VocalicosMenuActivity.super.onBackPressed();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*loadVocales();*/
    }

    public void visualizarLogroPrimerFonema(Integer logro_id){

        apiClient.agregar_logro(paciente_id, logro_id).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.body().getCode().equals("200")){
                    Log.e("Logro PrimerFonema", "Logro Primer Fonema");
                    Toast.makeText(getApplicationContext(),
                            "Logro conseguido: Primer Fonema",
                            Toast.LENGTH_SHORT)
                            .show();
                }else {
                    Log.e("No Logro PrimerFonema", "No Logro Primer Fonema");
                    Toast.makeText(getApplicationContext(),
                            "No se pudo agregar logro",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.e("ObteniendoPacienteLogro", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void checkifobtuvoLogroPrimerFonema(){
        apiClient.listarlogroxusuarioid(paciente_id).enqueue(new Callback<List<PacienteLogro>>() {
            @Override
            public void onResponse(Call<List<PacienteLogro>> call, Response<List<PacienteLogro>> response) {
                pacienteLogroList = response.body();

                if(!pacienteLogroList.isEmpty()){
                    for(PacienteLogro pl:pacienteLogroList){
                        if(pl.getLogro_id() == 3){
                            return;
                        }
                    }
                    visualizarLogroPrimerFonema(3);
                    return;
                }else {
                    visualizarLogroPrimerFonema(3);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<PacienteLogro>> call, Throwable t) {
                Log.e("ObteniendoPacienteLogro", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void loadVocales() {
        progressBarMenu.setVisibility(View.VISIBLE);

        apiClient.listar_sesionfonemasxusuario(paciente_id).enqueue(new Callback<List<SesionFonema>>() {
            @Override
            public void onResponse(Call<List<SesionFonema>> call, Response<List<SesionFonema>> response) {
                progressBarMenu.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    sesionFonemaList = response.body();
                    List<Banderin> banderines = new ArrayList<>();

                    for (SesionFonema v : sesionFonemaList) {
                        banderines.add(new Banderin(v.getFonema().getNombre()));
                        if(v.getCompletado()==1){
                            checkifobtuvoLogroPrimerFonema();
                            break;
                        }
                    }
                    vocalAdapter.setData(banderines, new ConsonantesAdapter.OnConsonantesListener() {
                        @Override
                        public void onConsonanteClick(int position) {
                            OnVocalClick(position);
                        }
                    });
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
            public void onFailure(Call<List<SesionFonema>> call, Throwable t) {
                progressBarMenu.setVisibility(View.GONE);
                Log.e("Obteniendo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void OnVocalClick(int position) {
        SesionFonema v = sesionFonemaList.get(position);
        Intent intent = new Intent(this, VocalicosSesionActivity.class);
        intent.putExtra("id_fonema", v.getFonema_id());
        intent.putExtra("id_sesion_fonema", v.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVocales();
    }
}
