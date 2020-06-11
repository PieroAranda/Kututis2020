package com.example.kututistesis.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.dialog.BirthDatePickerFragment;
import com.example.kututistesis.model.SesionPraxia;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialVideosFechas extends AppCompatActivity {

    private ApiClient apiClient;
    private String url;
    private File fileVideo;
    private VideoView video;
    private ImageView imageView;
    private ProgressBar progressBarVideos;
    private EditText editText;

    private RecyclerView recyclerView;
    private HistorialVideosFechasAdapter videosFechasAdapter;
    private MediaController mediaController;

    private Integer id_paciente;
    private Integer id_praxia;

    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_videos_por_fechas);

        globalClass = (GlobalClass) getApplicationContext();

        Intent intent = getIntent();

        Integer intent_praxia_id = intent.getIntExtra("praxia_id",0);

        apiClient = ApiClient.getInstance();

        editText = findViewById(R.id.edit_text_date);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDatePicker();
            }
        });

        video = (VideoView) findViewById(R.id.VideoSeleccionado);

        recyclerView = findViewById(R.id.recyclerHistorialVideoPorFecha);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        videosFechasAdapter = new HistorialVideosFechasAdapter();

        imageView = findViewById(R.id.Boton_Buscar_Videos);

        mediaController = new MediaController(this);

        url = "http://192.168.0.7:82/curso-laravel/kututis/";

        id_paciente = globalClass.getId_usuario();
        id_praxia = intent_praxia_id;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = editText.getText().toString();
                if (fecha.length() != 0) {
                    obtenerVideosGrabados(id_praxia, id_paciente, fecha);
                }
            }
        });


    }

    public void obtenerVideosGrabados(Integer id_praxia, Integer id_paciente,String fecha){
        apiClient.buscarxpraxiaxusuarioxfecha(id_praxia,id_paciente,fecha).enqueue(new Callback<List<SesionPraxia>>() {
            @Override
            public void onResponse(Call<List<SesionPraxia>> call, Response<List<SesionPraxia>> response) {
                List<SesionPraxia> sesionPraxiaList = response.body();
                //Log.d("Funciono praxias", sesionPraxiaList.get(0).getRuta());
                for (SesionPraxia sesionPraxia: sesionPraxiaList){
                    url = url + sesionPraxia.getRuta_servidor();
                    sesionPraxia.setRuta_servidor(url);
                    url = "http://192.168.0.7:82/curso-laravel/kututis/";
                }

                videosFechasAdapter.setData(sesionPraxiaList, video, mediaController);
                recyclerView.setAdapter(videosFechasAdapter);

            }

            @Override
            public void onFailure(Call<List<SesionPraxia>> call, Throwable t) {
                Log.d("Fallo praxias", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void openDialogDatePicker() {
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

        newFragment.show(HistorialVideosFechas.this.getSupportFragmentManager(), "datePicker");
    }

}
