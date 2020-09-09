package com.example.kututistesis.activities.vocalicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.HistorialAudiosFechasAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.dialog.BirthDatePickerFragment;
import com.example.kututistesis.model.ArchivoSesionFonema;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.util.Global;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VocalicosHistorialActivity extends AppCompatActivity {
    private ApiClient apiClient;
    private EditText editText;
    private RecyclerView recyclerView;
    private ConstraintLayout layoutNotFound;

    private HistorialAudiosFechasAdapter audiosFechasAdapter;
    private ImageView imageView;
    private String url;
    private Global global;
    private ImageView imageViewAtras;
    private ProgressBar progressBarBusqueda;

    private File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vocalicos_historial);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        global = (Global) getApplicationContext();

        Intent intent = getIntent();

        Integer intent_sesion_fonema_id = intent.getIntExtra("sesion_fonema_id",0);

        apiClient = ApiClient.getInstance();

        editText = findViewById(R.id.edit_text_date_audios);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDatePicker();
            }
        });

        recyclerView = findViewById(R.id.recyclerHistorialAudioPorFecha);
        imageViewAtras = findViewById(R.id.imageViewVocalicosHistorialAtras);
        layoutNotFound = findViewById(R.id.layoutVocalicosHistorialNotFound);
        progressBarBusqueda = findViewById(R.id.progressBarVocalicosHistorial);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        audiosFechasAdapter = new HistorialAudiosFechasAdapter();

        imageView = findViewById(R.id.Boton_Buscar_Audios);

        url = ApiClient.BASE_HOST_URL;

        final Integer id_paciente = global.getId_usuario();
        final Integer id_sesion_fonema = intent_sesion_fonema_id;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = editText.getText().toString();
                if (fecha.length() != 0) {
                    obtenerAudiosGrabados(id_sesion_fonema, fecha);
                }
            }
        });

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VocalicosHistorialActivity.super.onBackPressed();
            }
        });

        loadActualHistorial(id_sesion_fonema);
    }

    private void loadActualHistorial(int id_sesion_fonema) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        String todayDateFormatted = f.format(c.getTime());
        obtenerAudiosGrabados(id_sesion_fonema, todayDateFormatted);
        editText.setText(todayDateFormatted);
    }

    public void obtenerAudiosGrabados(Integer id_sesion_fonema, String fecha) {
        layoutNotFound.setVisibility(View.GONE);
        progressBarBusqueda.setVisibility(View.VISIBLE);

        // Elimina los resultados previos si los hubieron
        HistorialAudiosFechasAdapter adapter = new HistorialAudiosFechasAdapter();
        List<ArchivoSesionFonema> listaVacia = new ArrayList<>();
        adapter.setData(listaVacia, storageDir);
        recyclerView.setAdapter(adapter);

        apiClient.buscararchivosxsesionfonemaidxfecha(id_sesion_fonema, fecha).enqueue(new Callback<List<ArchivoSesionFonema>>() {
            @Override
            public void onResponse(Call<List<ArchivoSesionFonema>> call, Response<List<ArchivoSesionFonema>> response) {
                progressBarBusqueda.setVisibility(View.GONE);
                List<ArchivoSesionFonema> archivoSesionFonemas = response.body();
                Log.d("Funciono vocales", "tamanio:"+archivoSesionFonemas.size());

                if(archivoSesionFonemas.size() == 0) {
                    layoutNotFound.setVisibility(View.VISIBLE);
                } else {
                    layoutNotFound.setVisibility(View.GONE);
                }

                /*
                for (SesionVocal sesionVocal: sesionVocalList){
                    url = url + sesionVocal.getRuta_servidor();
                    sesionVocal.setRuta_servidor(url);
                    url = ApiClient.BASE_HOST_URL;
                }*/

                Collections.reverse(archivoSesionFonemas);

                audiosFechasAdapter.setData(archivoSesionFonemas, storageDir);
                recyclerView.setAdapter(audiosFechasAdapter);
            }

            @Override
            public void onFailure(Call<List<ArchivoSesionFonema>> call, Throwable t) {
                progressBarBusqueda.setVisibility(View.GONE);

                Log.d("Fallo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void openDialogDatePicker() {
        FragmentManager fm = this.getSupportFragmentManager();

        if (fm.findFragmentByTag("datePicker") != null) {
            return;
        }

        BirthDatePickerFragment newFragment = BirthDatePickerFragment.newInstance(false,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate;
                // +1 because January is zero
                if (day>=1 && day <=9){
                    selectedDate = "0"+day + "-" +"0"+ (month+1) + "-" + year;
                }else {
                    selectedDate = day + "-" +"0"+ (month+1) + "-" + year;
                }

                editText.setText(selectedDate);
            }
        });

        newFragment.show(fm, "datePicker");
    }
}
