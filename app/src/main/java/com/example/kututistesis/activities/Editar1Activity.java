package com.example.kututistesis.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.kututistesis.R;
import com.example.kututistesis.activities.registro.Registro1Activity;
import com.example.kututistesis.activities.registro.Registro2Activity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.dialog.BirthDatePickerFragment;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.util.Global;
import com.example.kututistesis.util.Validations;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Editar1Activity extends AppCompatActivity {
    private static final String INTENT_EXTRA_UPDATE_DATA = "UPDATE_DATA";

    private ImageView buttonNext;
    private EditText editTextBirthDate;
    private EditText editTextNames;
    private EditText editTextLastname;
    private EditText editTextMobileNumber;
    private AwesomeValidation awesomeValidation;
    private ImageView imageViewAtras;

    private Global global;
    private Integer paciente_id;
    private ApiClient apiClient;
    private SignUpForm datospaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_editar_perfil_1);

        // Inicializa los elementos de la vista
        editTextNames = (EditText) findViewById(R.id.edit_Nombre);
        editTextLastname = (EditText) findViewById(R.id.edit_Apellido);
        editTextBirthDate = (EditText) findViewById(R.id.edit_Nacimiento);
        editTextMobileNumber = (EditText) findViewById(R.id.edit_Celular);
        buttonNext = (ImageView) findViewById(R.id.button_siguiente_editar);
        imageViewAtras = (ImageView) findViewById(R.id.imageViewEditarPerfil1Atras);

        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();
        apiClient = ApiClient.getInstance();

        obtenerDatosPaciente(paciente_id);

        // Eventos de la vista
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditar2();
            }
        });
        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDatePicker();
            }
        });
        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar1Activity.super.onBackPressed();
            }
        });

        // Abre el dialogo para escoger la fecha nacimiento si se hace tap al botón next del teclado
        editTextLastname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    openDialogDatePicker();
                    return true;
                }
                return false;
            }
        });

        editTextMobileNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    goToEditar2();
                    return true;
                }
                return false;
            }
        });

        // Muestra el teclado para ingresar los nombres
        if (editTextNames.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        // Validaciones
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edit_Nombre, Validations.notBlank, R.string.err_names_blank);
        awesomeValidation.addValidation(this, R.id.edit_Apellido, Validations.notBlank, R.string.err_lastname_blank);
        awesomeValidation.addValidation(this, R.id.edit_RepetirContrasenia, Validations.notBlank, R.string.err_birth_blank);
        awesomeValidation.addValidation(this, R.id.edit_Celular, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                return s.trim().length() == 9;
            }
        }, R.string.err_mobile_phone_length);
    }

    public void ponerDatosEnEditTexts(SignUpForm datospaciente){
        editTextNames.setText(datospaciente.getNombre());
        editTextLastname.setText(datospaciente.getApellido());
        editTextBirthDate.setText(datospaciente.getFecha_nacimiento());
        editTextMobileNumber.setText(datospaciente.getCelular());
    }

    public void obtenerDatosPaciente(Integer paciente_id){
        apiClient.buscar_pacientexid(paciente_id).enqueue(new Callback<List<SignUpForm>>() {
            @Override
            public void onResponse(Call<List<SignUpForm>> call, Response<List<SignUpForm>> response) {
                datospaciente = response.body().get(0);
                ponerDatosEnEditTexts(datospaciente);
            }

            @Override
            public void onFailure(Call<List<SignUpForm>> call, Throwable t) {
                Log.d("FalloPaciente", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener los datos del paciente",
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

        BirthDatePickerFragment newFragment = BirthDatePickerFragment.newInstance(true,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                editTextBirthDate.setText(selectedDate);

                if(editTextMobileNumber.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        newFragment.show(fm, "datePicker");
    }

    private void goToEditar2() {
        if (awesomeValidation.validate()) {
            SignUpForm signUpData = new SignUpForm();
            signUpData.setNombre(editTextNames.getText().toString().trim());
            signUpData.setApellido(editTextLastname.getText().toString().trim());
            signUpData.setCelular(editTextMobileNumber.getText().toString().trim());
            signUpData.setFecha_nacimiento(editTextBirthDate.getText().toString().trim());

            Intent intent = new Intent(this, Editar2Activity.class);
            // Revisar si hacer un cast de Serializable es eficiente o mejor usar la
            // interface Parcelable
            intent.putExtra(INTENT_EXTRA_UPDATE_DATA, (Serializable) signUpData);
            startActivity(intent);
        }
    }
}
