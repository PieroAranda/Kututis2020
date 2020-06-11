package com.example.kututistesis.activities.vocalicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.adapters.HistorialAudiosFechasAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.dialog.BirthDatePickerFragment;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.util.GlobalClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialVocalicosActivity extends AppCompatActivity {
    private ApiClient apiClient;
    private EditText editText;
    private RecyclerView recyclerView;

    private HistorialAudiosFechasAdapter audiosFechasAdapter;
    private ImageView imageView;
    private String url;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_audios_por_fechas);

        globalClass = (GlobalClass) getApplicationContext();

        Intent intent = getIntent();

        Integer intent_vocal_id = intent.getIntExtra("vocal_id",0);

        apiClient = ApiClient.getInstance();

        editText = findViewById(R.id.edit_text_date_audios);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDatePicker();
            }
        });

        recyclerView = findViewById(R.id.recyclerHistorialAudioPorFecha);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        audiosFechasAdapter = new HistorialAudiosFechasAdapter();

        imageView = findViewById(R.id.Boton_Buscar_Audios);

        url = "http://192.168.0.7:82/curso-laravel/kututis/";

        final Integer id_paciente = globalClass.getId_usuario();
        final Integer id_vocal = intent_vocal_id;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = editText.getText().toString();
                if (fecha.length() != 0) {
                    obtenerAudiosGrabados(id_vocal, id_paciente, fecha);
                }
            }
        });

    }

    public void obtenerAudiosGrabados(Integer id_vocal, Integer id_paciente, String fecha) {
        apiClient.buscarxvocalxusuarioxfecha(id_vocal, id_paciente, fecha).enqueue(new Callback<List<SesionVocal>>() {
            @Override
            public void onResponse(Call<List<SesionVocal>> call, Response<List<SesionVocal>> response) {
                List<SesionVocal> sesionVocalList = response.body();
                Log.d("Funciono vocales", "tamanio:"+sesionVocalList.size());
                for (SesionVocal sesionVocal: sesionVocalList){
                    url = url + sesionVocal.getRuta_servidor();
                    sesionVocal.setRuta_servidor(url);
                    url = "http://192.168.0.7:82/curso-laravel/kututis/";
                }

                audiosFechasAdapter.setData(sesionVocalList);
                recyclerView.setAdapter(audiosFechasAdapter);
            }

            @Override
            public void onFailure(Call<List<SesionVocal>> call, Throwable t) {
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

        newFragment.show(HistorialVocalicosActivity.this.getSupportFragmentManager(), "datePicker");
    }
}
