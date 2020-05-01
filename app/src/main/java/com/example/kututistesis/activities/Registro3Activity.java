package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.api.ApiService;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SignUpForm;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Registro3Activity extends AppCompatActivity {

    private static final String INTENT_EXTRA_SIGN_UP_DATA = "SIGN_UP_DATA";

    private EditText editTextPetName;
    private Button buttonRegister;
    private SignUpForm signUpForm;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_registrar_3);

        getIntentData();

        editTextPetName = (EditText) findViewById(R.id.edit_text_pet_name);
        buttonRegister = (Button) findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        apiClient = ApiClient.getInstance();
    }

    private void getIntentData() {
        if(getIntent() != null){
            signUpForm = (SignUpForm) getIntent().getSerializableExtra(INTENT_EXTRA_SIGN_UP_DATA);
        }
    }

    private void registerUser() {
        if(isValid()) {
            // Se valida y se registra al usuario
            Log.i("SIGNUP", signUpForm.toString());
            apiClient.registrarPaciente(signUpForm).enqueue(new Callback<ResponseStatus>() {
                @Override
                public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                    Log.i("SIGNUP", response.body().getStatus() + " " + response.body().getCode());
                    String responseCode = response.body().getCode();

                    switch (responseCode) {
                        case "200":
                            goToPaginaPrincipal();
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

    private void goToPaginaPrincipal() {
        Intent intent = new Intent(this, PaginaPrincipalActivity.class);
        // Flags para que al registrarse descarte actividades del registro y se quede solo con la
        // actividad de la pantalla principal
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private boolean isValid() {
        return editTextPetName.getText().toString().trim().length() > 0;
    }
}
