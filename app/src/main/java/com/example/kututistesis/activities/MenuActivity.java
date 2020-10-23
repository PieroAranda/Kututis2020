package com.example.kututistesis.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Mascota;
import com.example.kututistesis.util.Global;
import com.example.kututistesis.util.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private ImageButton botonPracticar;
    private ImageButton botonSincronizar;
    private ImageButton botonAtras; //boton_monstruo
    private ImageButton botonMonstruo;
    private ImageButton botonLogros;
    private ImageButton botonPerfil;
    private ImageButton botonAcerca;
    private TextView golosinas;
    private ProgressBar progressBar;
    private ApiClient apiClient;
    private Global global;
    private Integer id_paciente;
    private Prefs preference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);
        preference = new Prefs(getApplicationContext());


        int defaultValue = -1;
        global = (Global) getApplicationContext();
        id_paciente = global.getId_usuario();


        botonPracticar = findViewById(R.id.boton_practicar);
        botonAtras = findViewById(R.id.boton_atras);
        botonMonstruo = findViewById(R.id.boton_monstruo);
        botonLogros = findViewById(R.id.boton_logros);
        botonPerfil = findViewById(R.id.boton_perfil);
        botonAcerca = findViewById(R.id.boton_acerca);

        apiClient = ApiClient.getInstance();
        golosinas = findViewById(R.id.golosinas);
        progressBar = findViewById(R.id.progress_menu);
        botonSincronizar = findViewById(R.id.boton_sincronizar);

        botonPracticar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenuPracticar();
            }
        });
        botonSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMascota(id_paciente);
            }
        });
        botonMonstruo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMonstruo();
            }
        });
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        botonLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogros();
            }
        });
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditarPerfil();
            }
        });
        botonAcerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAcerca();
            }
        });
        loadMascota(id_paciente);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMascota(id_paciente);
    }

    private void goToMenuPracticar() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
    }

    public void loadMascota(int pacienteId) {

       // progressBar.setVisibility(View.VISIBLE);

        Log.e("user id", String.valueOf(pacienteId));

        apiClient.getMascota(pacienteId).enqueue(new Callback<Mascota>() {
            @Override
            public void onResponse(Call<Mascota> call, Response<Mascota> response) {
                if (response.isSuccessful()) {
                    golosinas.setText(response.body().getCantidad_Dinero().toString());
                   // if( response.body() != null)
                   //     preference.saveMascota("mascota", response.body());


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ocurrió un problema",
                            Toast.LENGTH_SHORT)
                            .show();
                }
               // progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Mascota> call, Throwable t) {
                Log.e("Obteniendo mascota", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
               // progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void logoutDoing() {

        SharedPreferences.Editor editor = global.sharedPref.edit();
        editor.putInt(getString(R.string.saved_user_id), -1);
        editor.commit();

        Intent intent = new Intent(this, InicioSesionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void logout() {

        showAlertDialogLogout("Te vamos a echar de menos", "¿Estás seguro de cerrar sesión?");
    }

    private void goToMonstruo() {
        Intent intent = new Intent(this, MonstruoActivity.class);
        startActivity(intent);
    }

    private void goToLogros() {
        Intent intent = new Intent(this, MenuLogros.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToEditarPerfil() {
        Intent intent = new Intent(this, Editar1Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToAcerca() {
        Intent intent = new Intent(this, AcercaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        //showAlertDialog();
    }

    public void showAlertDialog(String title, String subtitle) {
        // create an alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.row_alert, null);
        builder.setView(customLayout);
        ImageButton button = customLayout.findViewById(R.id.alertButton);
        TextView mTitle = customLayout.findViewById(R.id.alertTitle);
        TextView mSubtitle = customLayout.findViewById(R.id.alertSubtitle);
        mTitle.setText(title);
        mSubtitle.setText(subtitle);
        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    // do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    public void showAlertDialogLogout(String title, String subtitle) {
        // create an alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.row_alert_logout, null);
        builder.setView(customLayout);
        ImageButton button = customLayout.findViewById(R.id.alertButton);
        TextView mTitle = customLayout.findViewById(R.id.alertTitle);
        TextView mSubtitle = customLayout.findViewById(R.id.alertSubtitle);
        mTitle.setText(title);
        mSubtitle.setText(subtitle);
        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                logoutDoing();
            }
        });
    }
}