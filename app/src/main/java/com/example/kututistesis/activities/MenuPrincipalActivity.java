package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.consonanticos.ConsonanticosMenu2Activity;
import com.example.kututistesis.activities.consonanticos.ConsonanticosMenuActivity;
import com.example.kututistesis.activities.praxias.PraxiasMenuActivity;
import com.example.kututistesis.activities.vocalicos.VocalicosMenuActivity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Mascota;
import com.example.kututistesis.util.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipalActivity extends AppCompatActivity {

    private ImageView imageViewPraxias;
    private ImageView imageViewConsonatesVocalicos;
    private ImageView imageViewConsonatesConsonanticos;
    private ImageView imageViewLogout;
    private Global global;
    private Button botonLogros;
    private Button botonPerfil;
    private TextView golosinas;
    private ApiClient apiClient;
    private Integer id_paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu_principal);
        global = (Global) getApplicationContext();
        id_paciente = global.getId_usuario();

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        apiClient = ApiClient.getInstance();

        // Inicializa los elementos de la vista
        imageViewPraxias = findViewById(R.id.button_praxias);
        imageViewConsonatesVocalicos = findViewById(R.id.button_fonemas_vocalicos);
        imageViewConsonatesConsonanticos = findViewById(R.id.button_fonemas_consonanticos);
        imageViewLogout = findViewById(R.id.image_logout);
        botonLogros = findViewById(R.id.buttonLogros);
        botonPerfil = findViewById(R.id.buttonPerfil);
        golosinas = findViewById(R.id.menu_golosinas);

        // Eventos de la vista
        imageViewPraxias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPraxias();
            }
        });

        imageViewConsonatesVocalicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonatesVocalicos();
            }
        });

        imageViewConsonatesConsonanticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonatesConsonanticos();
            }
        });

        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        botonLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogros();
            }
        });

        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditarPerfil();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMascota(id_paciente);
    }

    private void logout() {
        SharedPreferences.Editor editor = global.sharedPref.edit();
        editor.putInt(getString(R.string.saved_user_id), -1);
        editor.commit();

        Intent intent = new Intent(this, InicioSesionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToConsonatesConsonanticos() {
        Intent intent = new Intent(this, ConsonanticosMenu2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToConsonatesVocalicos() {
        Intent intent = new Intent(this, VocalicosMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToPraxias() {
        Intent intent = new Intent(this, PraxiasMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToLogros() {
        Intent intent = new Intent(this, MenuLogros.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToEditarPerfil() {
        Intent intent = new Intent(this, Editar1Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void loadMascota(int pacienteId) {


        Log.e("user id", String.valueOf(pacienteId));

        apiClient.getMascota(pacienteId).enqueue(new Callback<Mascota>() {
            @Override
            public void onResponse(Call<Mascota> call, Response<Mascota> response) {
                if (response.isSuccessful()) {
                    golosinas.setText(response.body().getCantidad_Dinero().toString());
                    // if( response.body() != null)
                    //     preference.saveMascota("mascota", response.body());


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

}
