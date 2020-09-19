package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Mascota;
import com.example.kututistesis.util.Global;
import com.example.kututistesis.util.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonstruoActivity extends AppCompatActivity {

    private TextView textViewMonstruoGolosinas;
    private TextView textViewMonstruoVida;
    private TextView textViewMonstruoNombre;
    private TextView textViewMonstruoHambre;
    private TextView textViewMonstruoEstado;
    private LinearLayout llAlimentarMonstruo;
    private ImageView imageViewMonstruo;
    private Button buttonBackMonstruo;
    private Global global;
    private Integer id_paciente;

    private Prefs preference;
    private Mascota mascota;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_monstruo);

        preference = new Prefs(getApplicationContext());
//        mascota = preference.getMascota("mascota");
        apiClient = ApiClient.getInstance();
        global = (Global) getApplicationContext();
        id_paciente = global.getId_usuario();

        textViewMonstruoGolosinas = findViewById(R.id.textViewMonstruoGolosinas);
        textViewMonstruoVida = findViewById(R.id.textViewMonstruoVida);
        textViewMonstruoNombre = findViewById(R.id.textViewMonstruoNombre);
        textViewMonstruoHambre = findViewById(R.id.textViewMonstruoHambre);
        //textViewMonstruoEstado = findViewById(R.id.textViewMonstruoEstado);
        llAlimentarMonstruo = findViewById(R.id.llHambre);
        imageViewMonstruo = findViewById(R.id.imageViewMonstruo);
        buttonBackMonstruo = findViewById(R.id.buttonBackMonstruo);

        llAlimentarMonstruo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alimentarMascota(id_paciente);
            }
        });
        buttonBackMonstruo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadMascota(id_paciente);
    }



    public void loadMascota(int pacienteId) {


        Log.e("user id", String.valueOf(pacienteId));

        apiClient.getMascota(pacienteId).enqueue(new Callback<Mascota>() {
            @Override
            public void onResponse(Call<Mascota> call, Response<Mascota> response) {
                if (response.isSuccessful()) {
                    Log.e("body", response.body().getCantidad_Dinero().toString());
                    if(response.body().getError() == null){
                        textViewMonstruoGolosinas.setText(response.body().getCantidad_Dinero().toString());
                        textViewMonstruoVida.setText(response.body().getVida() + "/100");
                        textViewMonstruoNombre.setText(response.body().getNombre());
                        textViewMonstruoHambre.setText(response.body().getHambre().toString());
                        if(response.body().getEstado() == 1)
                            imageViewMonstruo.setBackgroundResource(R.drawable.img_monstruo_feliz);
                        else
                            imageViewMonstruo.setBackgroundResource(R.drawable.img_monstruo_triste);
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "No cuenta con los caramelos suficientes para alimetar :(",
                                Toast.LENGTH_SHORT)
                                .show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Mascota> call, Throwable t) {
                Log.e("Obteniendo mascota", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void alimentarMascota(int pacienteId) {


        Log.e("user id", String.valueOf(pacienteId));

        apiClient.alimentarMascota(pacienteId).enqueue(new Callback<Mascota>() {
            @Override
            public void onResponse(Call<Mascota> call, Response<Mascota> response) {
                if (response.isSuccessful()) {

                    if(response.body().getError() == null || response.body().getError().isEmpty()){
                        textViewMonstruoGolosinas.setText(response.body().getCantidad_Dinero().toString());
                        textViewMonstruoVida.setText(response.body().getVida() + "/100");
                        textViewMonstruoNombre.setText(response.body().getNombre());
                        textViewMonstruoHambre.setText(response.body().getHambre().toString());
                        if(response.body().getEstado() == 1)
                            imageViewMonstruo.setBackgroundResource(R.drawable.img_monstruo_feliz);
                        else
                            imageViewMonstruo.setBackgroundResource(R.drawable.img_monstruo_triste);
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "No cuenta con los caramelos suficientes para alimentar :(",
                                Toast.LENGTH_SHORT)
                                .show();
                    }



                } else {
                    Toast.makeText(getApplicationContext(),
                            "No cuenta con las golosinas suficientes :(",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Mascota> call, Throwable t) {
                Log.e("Obteniendo mascota", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}