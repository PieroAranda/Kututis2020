package com.example.kututistesis.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.dialog.BirthDatePickerFragment;
import com.example.kututistesis.model.Medico;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.util.Global;
import com.example.kututistesis.util.Validations;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicoActivity extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextNames;
    private EditText editTextLastname;
    private EditText editTextMobileNumber;
    private ImageView imageViewAtras;

    private Global global;
    private Integer paciente_id;
    private ApiClient apiClient;
    private Medico datosMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu_medico);

        Intent intent = getIntent();
        Integer medico_id =  intent.getIntExtra("medico_id",0);

        // Inicializa los elementos de la vista
        editTextNames = (EditText) findViewById(R.id.edit_NombreMedico);
        editTextLastname = (EditText) findViewById(R.id.edit_ApellidoMedico);
        editTextCorreo = (EditText) findViewById(R.id.edit_CorreoMedico);
        editTextMobileNumber = (EditText) findViewById(R.id.edit_CelularMedico);
        imageViewAtras = (ImageView) findViewById(R.id.imageViewMedicoInfoAtras);

        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();
        apiClient = ApiClient.getInstance();

        obtenerDatosMedico(medico_id);

        // Eventos de la vista

        disableEditText(editTextNames);
        disableEditText(editTextLastname);
        disableEditText(editTextCorreo);
        disableEditText(editTextMobileNumber);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicoActivity.super.onBackPressed();
            }
        });

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        /*editText.setBackgroundColor(Color.TRANSPARENT);*/
    }

    public void ponerDatosEnEditTexts(Medico datosMedico){
        editTextNames.setText(datosMedico.getNombre());
        editTextLastname.setText(datosMedico.getApellido());
        editTextCorreo.setText(datosMedico.getCorreo());
        editTextMobileNumber.setText(datosMedico.getCelular());
    }

    public void obtenerDatosMedico(Integer medico_id){
        apiClient.buscar_medicoxid(medico_id).enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
                datosMedico = response.body().get(0);
                ponerDatosEnEditTexts(datosMedico);
            }

            @Override
            public void onFailure(Call<List<Medico>> call, Throwable t) {
                Log.d("FalloMedico", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener los datos del medico",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
