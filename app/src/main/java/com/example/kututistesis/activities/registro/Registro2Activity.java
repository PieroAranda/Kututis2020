package com.example.kututistesis.activities.registro;

import androidx.appcompat.app.AppCompatActivity;

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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SignUpForm;

import java.io.Serializable;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro2Activity extends AppCompatActivity {

    private static final String INTENT_EXTRA_SIGN_UP_DATA = "SIGN_UP_DATA";

    private ImageView buttonNext;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private ImageView imageViewAtras;

    private SignUpForm signUpForm;
    private AwesomeValidation awesomeValidation;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_registrar_2);

        getIntentData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword1 = (EditText) findViewById(R.id.edit_text_password_1);
        editTextPassword2 = (EditText) findViewById(R.id.edit_text_password_2);
        buttonNext = (ImageView) findViewById(R.id.button_next);
        imageViewAtras = (ImageView) findViewById(R.id.imageViewRegistro2Atras);

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
                Registro2Activity.super.onBackPressed();
            }
        });

        if(editTextEmail.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        // Validaciones
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edit_text_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        awesomeValidation.addValidation(this, R.id.edit_text_password_1, new SimpleCustomValidation() {
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
        awesomeValidation.addValidation(this, R.id.edit_text_password_1, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                return s.trim().length() >= 8;
            }
        }, R.string.err_password_length);
        awesomeValidation.addValidation(this, R.id.edit_text_password_2, R.id.edit_text_password_1, R.string.err_password_confirmation);

        apiClient = ApiClient.getInstance();
    }

    // Obtiene datos de la vista Registro 1
    private void getIntentData() {
        if(getIntent() != null) {
            signUpForm = (SignUpForm) getIntent().getSerializableExtra(INTENT_EXTRA_SIGN_UP_DATA);
            //Log.i("SIGNUP", signUpForm.getNombre() + " " + signUpForm.getApellido() + " " + signUpForm.getCelular());
        }
    }

    private void validate() {
        if(awesomeValidation.validate()) {
            String correo = editTextEmail.getText().toString().trim();
            String contrasenia = editTextPassword1.getText().toString().trim();

            apiClient.loginPaciente(correo, "WRONG_PASSWORD").enqueue(new Callback<ResponseStatus>() {
                @Override
                public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                    Log.i("SIGNUP", response.body().getStatus() + " " + response.body().getCode());
                    String responseStatus = response.body().getStatus();
                    // Verifica que el API responda que el correo no está registrado
                    if (responseStatus.matches("error_correo")) {
                        goToRegistro3();
                    } else {
                        // Muestra el mensaje de error cuando el correo sí está registrado
                        editTextEmail.setError(getString(R.string.err_email_used));
                        editTextEmail.requestFocus();
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

    private void goToRegistro3() {
        Intent intent = new Intent(this, Registro3Activity.class);

        signUpForm.setCorreo(editTextEmail.getText().toString().trim());
        signUpForm.setContrasenia(editTextPassword1.getText().toString().trim());
        intent.putExtra(INTENT_EXTRA_SIGN_UP_DATA, (Serializable) signUpForm);

        startActivity(intent);
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
