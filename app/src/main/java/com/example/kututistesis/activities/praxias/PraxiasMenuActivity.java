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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.ConsonantesAdapter;
import com.example.kututistesis.adapters.MenuBanderaAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Banderin;
import com.example.kututistesis.model.PacienteLogro;
import com.example.kututistesis.model.Praxia;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocabulario;
import com.example.kututistesis.util.Global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PraxiasMenuActivity extends AppCompatActivity {

    private ApiClient apiClient;
    private RecyclerView recyclerView;
    private MenuBanderaAdapter praxiasAdapter;
    private List<Praxia> praxiasList;
    private List<SesionPraxia> sesionPraxiaList;
    private ImageView imageViewAtras;
    private ProgressBar progressBarMenu;
    private Global global;
    private Integer paciente_id;
    private List<PacienteLogro> pacienteLogroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_praxias_menu);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();

        recyclerView = findViewById(R.id.recyclerViewPraxias);
        imageViewAtras = findViewById(R.id.imageViewPraxiasAtras);
        progressBarMenu = findViewById(R.id.progressBarPraxiasMenu);

        apiClient = ApiClient.getInstance();

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PraxiasMenuActivity.super.onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        praxiasAdapter = new MenuBanderaAdapter();

        loadPraxias();
    }

    public void visualizarLogroPrimeraMueca(Integer logro_id){

        apiClient.agregar_logro(paciente_id, logro_id).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.body().getCode().equals("200")){
                    Log.e("Logro PrimeraMueca", "Logro Primera Mueca");
                    Toast.makeText(getApplicationContext(),
                            "Logro conseguido: Primera Mueca",
                            Toast.LENGTH_SHORT)
                            .show();
                }else {
                    Log.e("No Logro PrimeraMueca", "No Logro Primera Mueca");
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

    public void checkifobtuvoLogroPrimeraMueca(){
        apiClient.listarlogroxusuarioid(paciente_id).enqueue(new Callback<List<PacienteLogro>>() {
            @Override
            public void onResponse(Call<List<PacienteLogro>> call, Response<List<PacienteLogro>> response) {
                pacienteLogroList = response.body();

                if(!pacienteLogroList.isEmpty()){
                    for(PacienteLogro pl:pacienteLogroList){
                        if(pl.getLogro_id() == 2){
                            return;
                        }
                    }
                    visualizarLogroPrimeraMueca(2);
                    return;
                }else {
                    visualizarLogroPrimeraMueca(2);
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

    public void loadPraxias() {
        progressBarMenu.setVisibility(View.VISIBLE);

        apiClient.listar_sesionpraxiasxusuario(paciente_id).enqueue(new Callback<List<SesionPraxia>>() {
            @Override
            public void onResponse(Call<List<SesionPraxia>> call, Response<List<SesionPraxia>> response) {
                progressBarMenu.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    sesionPraxiaList = response.body();
                    List<Banderin> banderines = new ArrayList<>();

                    for (SesionPraxia prax : sesionPraxiaList) {
                        /*String urlVideo = ApiClient.BASE_STORAGE_IMAGE_URL + prax.getPraxia().getVideo();
                        prax.getPraxia().setVideo(urlVideo);
                        String urlImagen = ApiClient.BASE_STORAGE_IMAGE_URL + prax.getPraxia().getImagen();
                        prax.getPraxia().setImagen(urlImagen);*/
                        banderines.add(new Banderin(prax.getPraxia().getNombre()));
                        if(prax.getCompletado()==1){
                            checkifobtuvoLogroPrimeraMueca();
                            break;
                        }
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
            public void onFailure(Call<List<SesionPraxia>> call, Throwable t) {
                progressBarMenu.setVisibility(View.GONE);
                Log.e("Obteniendo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void onPraxiaClick(int position) {
        SesionPraxia p = sesionPraxiaList.get(position);
        Intent intent = new Intent(this, PraxiasSesionActivity.class);
        intent.putExtra("id_praxia", p.getPraxias_id());
        intent.putExtra("id_sesion_praxia", p.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*loadPraxias();*/
    }
}
