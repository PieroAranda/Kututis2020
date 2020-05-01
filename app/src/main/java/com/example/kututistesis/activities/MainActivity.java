package com.example.kututistesis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textSignUp;
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_inicio_sesion);

        // Inicializa los elementos de la vista
        textSignUp = (TextView) findViewById(R.id.text_sign_up);
        buttonSignIn = (Button) findViewById(R.id.button_sign_in);
        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);

        // Subraya el texto para ir a la vista de registro
        textSignUp.setPaintFlags(textSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Eventos de la vista
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPaginaPrincipal();
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

        apiClient = ApiClient.getInstance();
    }

    private void goToRegistro() {
        Intent intent = new Intent(this, Registro1Activity.class);
        startActivity(intent);
    }

    private void login() {
        if(isValid()) {
            String email = editTextEmail.getText().toString().trim();
            String contrasenia = editTextPassword.getText().toString().trim();

            apiClient.loginPaciente(email, contrasenia).enqueue(new Callback<ResponseStatus>() {
                @Override
                public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                    Log.i("SIGNIN", response.body().getStatus() + " " + response.body().getCode());
                    String responseCode = response.body().getCode();

                    switch (responseCode) {
                        case "200":
                            goToPaginaPrincipal();
                            break;
                        case "400":
                            Toast.makeText(getApplicationContext(),
                                    "Ocurrío un problema, no se pudo autenticar",
                                    Toast.LENGTH_SHORT)
                                    .show();
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
    }

    private void goToPaginaPrincipal() {
        Intent intent = new Intent(this, PaginaPrincipalActivity.class);
        startActivity(intent);
    }

    private boolean isValid() {
        String email = editTextEmail.getText().toString().trim();
        return (email.length() > 0) &&
                (editTextPassword.getText().toString().trim().length() >= 8) &&
                (editTextPassword.getText().toString().trim().length() <= 16) &&
                Util.isEmailValid(email);
    }

}
