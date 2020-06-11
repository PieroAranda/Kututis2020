package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textSignUp;
    private ImageView buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ApiClient apiClient;
    private AwesomeValidation awesomeValidation;
    private GlobalClass global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.paciente_inicio_sesion);
        apiClient = ApiClient.getInstance();
        global = (GlobalClass) getApplicationContext();

        // Inicializa los elementos de la vista
        textSignUp = findViewById(R.id.text_sign_up);
        buttonSignIn = findViewById(R.id.button_sign_in);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        // Subraya el texto para ir a la vista de registro
        textSignUp.setPaintFlags(textSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Eventos de la vista
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
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

    private void goToRegistro() {
        Intent intent = new Intent(this, Registro1Activity.class);
        startActivity(intent);
    }

    private void login() {
        if (!awesomeValidation.validate()) {
            return;
        }
        String email = editTextEmail.getText().toString().trim();
        String contrasenia = editTextPassword.getText().toString().trim();

        apiClient.loginPaciente(email, contrasenia).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                Log.i("SIGNIN", response.body().getStatus() + " " + response.body().getCode());
                String responseCode = response.body().getCode();
                String responseStatus = response.body().getStatus();
                Integer responseId = response.body().getUser();

                switch (responseCode) {
                    case "200":
                        global.setId(responseId);
                        goToPaginaPrincipal();
                        break;
                    case "400":
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
                Log.e("SIGNIN", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrío un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void goToPaginaPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
