package com.example.kututistesis.activities.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.kututistesis.R;
import com.example.kututistesis.activities.BienvenidaActivity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.util.Global;
import com.example.kututistesis.util.Validations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro3Activity extends AppCompatActivity {

    private static final String INTENT_EXTRA_SIGN_UP_DATA = "SIGN_UP_DATA";

    private EditText editTextPetName;
    private ImageView buttonRegister;
    private ImageView imageViewAtras;
    private SignUpForm signUpForm;
    private ApiClient apiClient;
    private AwesomeValidation awesomeValidation;
    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro3);
        global = (Global) getApplicationContext();

        getIntentData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFechaInscripcion();

        editTextPetName = (EditText) findViewById(R.id.edit_text_pet_name);
        buttonRegister = (ImageView) findViewById(R.id.button_register);
        imageViewAtras = (ImageView) findViewById(R.id.imageViewRegistro3Atras);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro3Activity.super.onBackPressed();
            }
        });

        apiClient = ApiClient.getInstance();

        // Validaciones
        awesomeValidation =  new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edit_text_pet_name, Validations.notBlank, R.string.err_pet_name_blank);
    }

    private void getIntentData() {
        if(getIntent() != null){
            signUpForm = (SignUpForm) getIntent().getSerializableExtra(INTENT_EXTRA_SIGN_UP_DATA);
        }
    }

    private void getFechaInscripcion(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        signUpForm.setFecha_inscripcion(dateFormat.format(date));
    }

    private void registerUser() {
        if(awesomeValidation.validate()) {
            // Se valida y se registra al usuario
            Log.i("SIGNUP", signUpForm.toString());
            apiClient.registrarPaciente(signUpForm).enqueue(new Callback<ResponseStatus>() {
                @Override
                public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                    Log.i("SIGNUP", response.body().getStatus() + " " + response.body().getCode());
                    String responseCode = response.body().getCode();

                    switch (responseCode) {
                        case "200":
                            login(signUpForm.getCorreo(), signUpForm.getContrasenia());
                            break;
                        case "300":
                            Toast.makeText(getApplicationContext(),
                                    "Ocurrío un problema, no se pudo registrar el usuario",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ResponseStatus> call, Throwable t) {
                    Log.e("SIGNUP", t.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Ocurrío un problema, no se puede conectar al servicio",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });
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

    public void login(String email, String contrasenia) {
        apiClient.loginPaciente(email, contrasenia).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                Log.i("SIGNIN", response.body().getStatus() + " " + response.body().getCode());
                String responseCode = response.body().getCode();
                String responseStatus = response.body().getStatus();
                Integer responseId = response.body().getUser();

                switch (responseCode) {
                    case "200":
                        SharedPreferences.Editor editor = global.sharedPref.edit();
                        editor.putInt(getString(R.string.saved_user_id), responseId);
                        editor.commit();
                        global.setId(responseId);
                        goToBienvenida();
                        break;
                    default:
                        break;
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
}
