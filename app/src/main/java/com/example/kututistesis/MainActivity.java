package com.example.kututistesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textSignUp;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.paciente_inicio_sesion);

        // Inicializa los elementos de la vista
        textSignUp = (TextView) findViewById(R.id.text_sign_up);
        buttonSignIn = (Button) findViewById(R.id.button_sign_in);

        // Subraya el texto para ir a la vista de registro
        textSignUp.setPaintFlags(textSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Eventos de la vista
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPaginaPrincipal();
            }
        });
    }

    private void goToPaginaPrincipal() {
        Intent intent = new Intent(this, PaginaPrincipalActivity.class);
        startActivity(intent);
    }
}
