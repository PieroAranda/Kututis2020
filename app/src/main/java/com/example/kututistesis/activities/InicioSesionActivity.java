package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.kututistesis.R;
import com.example.kututistesis.activities.registro.Registro1Activity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.util.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioSesionActivity extends AppCompatActivity {

    private TextView textViewSignUp;
    private ImageView imageViewSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBarSignIn;
    private ApiClient apiClient;
    private AwesomeValidation awesomeValidation;
    private Global global;
    private int attemps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_inicio_sesion);
        apiClient = ApiClient.getInstance();
        global = (Global) getApplicationContext();
        global.sharedPref = getPreferences(Context.MODE_PRIVATE);

        // Verifica si el usuario ya se ha autenticado
        checkIfSignIn();

        // Inicializa los elementos de la vista
        textViewSignUp = findViewById(R.id.text_sign_up);
        imageViewSignIn = findViewById(R.id.button_sign_in);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        progressBarSignIn = findViewById(R.id.progressBarSignIn);

        // Subraya el texto para ir a la vista de registro
        textViewSignUp.setPaintFlags(textViewSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Eventos de la vista
        imageViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistro();
            }
        });

        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });

        // Esconde el mensaje de error en el campo del correo al editarse
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextPassword.setError(null);
            }
        });

        // Validaciones
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edit_text_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        awesomeValidation.addValidation(this, R.id.edit_text_password, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                s = s.trim();
                return s.length() >= 8 && s.length() <= 16;
            }
        }, R.string.err_password);
    }

    private void checkIfSignIn() {
        int defaultValue = -1;
        int userId = global.sharedPref.getInt(getString(R.string.saved_user_id), defaultValue);
        Log.i("USER_ID", "value: " + userId);
        if (userId != defaultValue) {
            global.setId(userId);
            goToMenuPrincipal();
        }
    }

    private void login() {
        if (!awesomeValidation.validate()) {
            return;
        }
        String email = editTextEmail.getText().toString().trim();
        String contrasenia = editTextPassword.getText().toString().trim();
        progressBarSignIn.setVisibility(View.VISIBLE);
        attemps = attemps + 1;

        apiClient.loginPaciente(email, contrasenia).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                Log.i("SIGNIN", response.body().getStatus() + " " + response.body().getCode());
                String responseCode = response.body().getCode();
                String responseStatus = response.body().getStatus();
                Integer responseId = response.body().getUser();

                switch (responseCode) {
                    case "200":
                        finish();
                        SharedPreferences.Editor editor = global.sharedPref.edit();
                        editor.putInt(getString(R.string.saved_user_id), responseId);
                        editor.commit();
                        global.setId(responseId);
                        goToMenuPrincipal();
                        break;
                    case "400":
                        progressBarSignIn.setVisibility(View.GONE);
                        if (responseStatus.matches("error_correo")) {
                            awesomeValidation.clear();
                            editTextEmail.setError(getString(R.string.err_email_not_registered));
                            editTextEmail.requestFocus();
                        } else if (responseStatus.matches("error_contra")) {
                            editTextPassword.setError(getString(R.string.err_password_incorrect));
                            editTextPassword.requestFocus();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Ocurrío un problema, no se pudo autenticar",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                if (attemps == 1) {
                    login();
                } else {
                progressBarSignIn.setVisibility(View.GONE);
                attemps = 0;
                Toast.makeText(getApplicationContext(),
                        "Ocurrío un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });
    }

    private void goToMenuPrincipal() {
        //Intent intent = new Intent(this, MenuPrincipalActivity.class);
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToRegistro() {
        Intent intent = new Intent(this, Registro1Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
