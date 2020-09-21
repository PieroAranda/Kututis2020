package com.example.kututistesis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.kututistesis.R;
import com.example.kututistesis.activities.registro.Registro2Activity;
import com.example.kututistesis.activities.registro.Registro3Activity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.util.Global;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Editar2Activity extends AppCompatActivity {
    private static final String INTENT_EXTRA_UPDATE_DATA = "UPDATE_DATA";

    private ImageView buttonNext;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private ImageView imageViewAtras;

    private SignUpForm signUpForm;
    private AwesomeValidation awesomeValidation;
    private ApiClient apiClient;

    private Global global;
    private Integer paciente_id;

    private SignUpForm datospaciente;

    private String correo_antiguo;

    private String fecha_inscripcion;
    private Integer Habilitado;
    private Integer medico_id;
    private Integer mascota_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_perfil_2);

        getIntentData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = (EditText) findViewById(R.id.edit_Email);
        editTextPassword1 = (EditText) findViewById(R.id.edit_Contrasenia);
        editTextPassword2 = (EditText) findViewById(R.id.edit_RepetirContrasenia);
        buttonNext = (ImageView) findViewById(R.id.button_Editar);
        imageViewAtras = (ImageView) findViewById(R.id.imageViewEditarPerfil2Atras);

        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();
        apiClient = ApiClient.getInstance();

        obtenerDatosPaciente(paciente_id);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        editTextPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    validate();
                    return true;
                }
                return false;
            }
        });

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar2Activity.super.onBackPressed();
            }
        });

        if(editTextEmail.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        // Validaciones
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edit_Email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        awesomeValidation.addValidation(this, R.id.edit_Contrasenia, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                // En caso se agrege la restricción de que la contraseña contenga una letra en mayúscula
                //Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                // Otras restricciones disponibles
                //Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                //Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                return specialCharPatten.matcher(s.trim()).find();
            }
        }, R.string.err_password_special_char);
        awesomeValidation.addValidation(this, R.id.edit_Contrasenia, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                return s.trim().length() >= 8;
            }
        }, R.string.err_password_length);
        awesomeValidation.addValidation(this, R.id.edit_RepetirContrasenia, R.id.edit_Contrasenia, R.string.err_password_confirmation);
    }

    public void ponerDatosEnEditTexts(SignUpForm datospaciente){
        editTextEmail.setText(datospaciente.getCorreo());
        editTextPassword1.setText(datospaciente.getContrasenia());
        editTextPassword2.setText(datospaciente.getContrasenia());
        correo_antiguo = datospaciente.getCorreo();
        Habilitado = datospaciente.getHabilitado();
        medico_id = datospaciente.getMedicoId();
        mascota_id = datospaciente.getMascotaId();
        fecha_inscripcion = datospaciente.getFecha_inscripcion();
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
                Log.d("FalloPaciente2", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener los datos del paciente",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    // Obtiene datos de la vista Registro 1
    private void getIntentData() {
        if(getIntent() != null) {
            signUpForm = (SignUpForm) getIntent().getSerializableExtra(INTENT_EXTRA_UPDATE_DATA);
            //Log.i("SIGNUP", signUpForm.getNombre() + " " + signUpForm.getApellido() + " " + signUpForm.getCelular());
        }
    }

    private void validate() {
        if(awesomeValidation.validate()) {
            String correo = editTextEmail.getText().toString().trim();
            String contrasenia = editTextPassword1.getText().toString().trim();

            if(correo.equals(correo_antiguo)){
                EdiarDatos();
            }else {
                EdiarDatos();
                /*apiClient = ApiClient.getInstance();
                apiClient.loginPaciente(correo, "WRONG_PASSWORD").enqueue(new Callback<ResponseStatus>() {
                    @Override
                    public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                        if (response.body().getStatus().equals("error_correo")) {
                            EdiarDatos();
                        } else {
                            // Muestra el mensaje de error cuando el correo sí está registrado
                            editTextEmail.setError(getString(R.string.err_email_used));
                            editTextEmail.requestFocus();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatus> call, Throwable t) {

                    }
                });*/
            }

        }
    }

    private void goToBienvenida() {
        Intent intent = new Intent(this, BienvenidaActivity.class);
        // Flags para que al registrarse descarte actividades del registro y se quede solo con la
        // actividad del mensaje de bienvenida
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void EdiarDatos() {
        signUpForm.setCorreo(editTextEmail.getText().toString().trim());
        signUpForm.setContrasenia(editTextPassword1.getText().toString().trim());

        apiClient.actualizar_paciente(paciente_id, signUpForm).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if(response.body().getCode().equals("200")){
                    Toast.makeText(getApplicationContext(),
                            "Datos del paciente actualizados",
                            Toast.LENGTH_SHORT)
                            .show();

                    goToBienvenida();
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Datos del paciente no fueron actualizados",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Ocurrío un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
