package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.util.Global;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsonanticosResultadosActivity extends AppCompatActivity {

    private ImageView imageViewAtras;
    private ImageView imageViewRepetir;
    private ImageView imageViewContenedor;
    private TextView textViewPuntaje;
    private TextView textViewCaramelos;
    private Integer puntaje;
    private int consonanteId;
    String url;
    String textoPalabra;
    private Global global;
    private Integer paciente_id;

    private ApiClient apiClient;
    private Integer id_sesion_vocabulario;
    private String Fecha;

    private Integer repeticiones;
    private Integer intentos_malos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consonanticos_resultados);

        global = (Global) getApplicationContext();
        paciente_id = global.getId_usuario();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        Fecha = dateFormat.format(date);


        imageViewAtras = findViewById(R.id.imageViewConsonanticosResultadosAtras);
        imageViewRepetir = findViewById(R.id.imageViewConsonanticosResultadosRepetir);
        textViewPuntaje = findViewById(R.id.textViewConsonanticosResultadosPuntaje);
        textViewCaramelos = findViewById(R.id.textViewConsonanticosResultadosCaramelos);
        imageViewContenedor = findViewById(R.id.imageViewConsonanticosResultadosContenedor);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonanticosMenu();
            }
        });

        imageViewRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConsonanticosSesion();
            }
        });

        Intent intent = getIntent();
        puntaje = intent.getIntExtra("puntaje", 0);
        consonanteId = intent.getIntExtra("consonante_id", -1);
        textoPalabra = intent.getStringExtra("texto_palabra");
        url = intent.getStringExtra("imagen_palabra");
        id_sesion_vocabulario = intent.getIntExtra("id_sesion_vocabulario", -1);
        repeticiones = intent.getIntExtra("repeticiones", -1);

        intentos_malos = repeticiones - puntaje;

        apiClient = ApiClient.getInstance();

        actualizarSesionVocabulario();
        showPuntaje();
    }

    private void goToConsonanticosSesion() {
        finish();
        Intent intent = new Intent(this, ConsonanticosSesionActivity.class);
        intent.putExtra("imagen_palabra", url);
        intent.putExtra("texto_palabra", textoPalabra);
        intent.putExtra("consonante_id", consonanteId);
        intent.putExtra("id_sesion_vocabulario", id_sesion_vocabulario);
        intent.putExtra("repeticiones", repeticiones);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void actualizarSesionVocabulario(){
        apiClient.actualizarsesion_vocabulario(id_sesion_vocabulario, puntaje, intentos_malos, 0, Fecha, paciente_id).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if(response.body().getCode().equals("200")){
                    Log.d("FuncionoSesionVoca", "Funcoooooo");
                    Toast.makeText(getApplicationContext(),
                            "Sesión Vocabulario Actualizada",
                            Toast.LENGTH_SHORT)
                            .show();
                }else{
                    Log.d("FalloSesionVoca", "Fallooooo");
                    Toast.makeText(getApplicationContext(),
                            "Sesión Vocabulario Actualizada FALLO",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.d("FalloSesionVocabulario", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener el video",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    private void showPuntaje() {
        imageViewContenedor.setImageResource((puntaje == repeticiones) ? R.drawable.fondo_felicitaciones : R.drawable.fondo_sigue_intentando);
        textViewPuntaje.setText(String.valueOf(puntaje) + " / "+repeticiones);
        textViewCaramelos.setText(puntaje.toString());
    }

    private void goToConsonanticosMenu() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("consonante_id", consonanteId);
        NavUtils.navigateUpTo(this, intent);
    }

}
