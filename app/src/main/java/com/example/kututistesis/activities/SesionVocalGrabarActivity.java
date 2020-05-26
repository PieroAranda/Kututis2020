package com.example.kututistesis.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocal;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionVocalGrabarActivity extends AppCompatActivity {
    private MediaRecorder grabacion;
    private Button btn_recorder;

    private Button reproducir;
    private TextView editText;

    private static final String CARPETA_PRINCIPAL = "misAudiosApp/";
    private static final String CARPETA_AUDIO = "audios";
    private static final String DIRECTORIO_AUDIO = CARPETA_PRINCIPAL + CARPETA_AUDIO;
    private String path;
    File fileVideo;
    private ApiClient apiClient;

    private Integer paciente_id;
    private Integer vocales_id;
    private Integer Aprobado;
    private String Fecha;
    private String ruta;

    private static final int COD_VIDEO = 20;
    private Button buttonEnviar;
    private Button buttonReproducir;
    private Boolean grabado = false;
    private Button buttonHistorialAudios;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_vocal_grabar);

        globalClass = (GlobalClass) getApplicationContext();

        Intent intent = getIntent();

        Integer intent_vocal_id = intent.getIntExtra("vocal_id",0);

        apiClient = ApiClient.getInstance();

        paciente_id = globalClass.getId_usuario();
        vocales_id = intent_vocal_id;
        Aprobado = 0;
        Fecha = "";
        ruta = "";

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) { //estrutura condicional que revisa si se ha puesto los permisos para grabar audio y escribir en el celular los audios, en el archivo manifest
            ActivityCompat.requestPermissions(SesionVocalGrabarActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000); //escribe en pantalla las ventanas empergentes que piden permisos para acceder al microfno y que se guarden los audios en el celular
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) { //estrutura condicional que revisa si se ha puesto los permisos para grabar audio y escribir en el celular los audios, en el archivo manifest
            ActivityCompat.requestPermissions(SesionVocalGrabarActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1000); //escribe en pantalla las ventanas empergentes que piden permisos para acceder al microfno y que se guarden los audios en el celular
        }

        btn_recorder = (Button) findViewById(R.id.btn_rec);
        buttonEnviar = (Button) findViewById(R.id.buttonEnvio2);
        buttonReproducir = (Button) findViewById(R.id.btn_play);
        reproducir = findViewById(R.id.btn_play);

        buttonHistorialAudios = findViewById(R.id.buttonHistorialAudios);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarAudio();
            }
        });

        buttonReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grabado) {
                    reproducir();
                }
            }
        });
    }
    public void Recorder(View view){
        buttonHistorialAudios.setEnabled(false);
        if(grabacion == null){
                Long consecutivo = System.currentTimeMillis()/1000;
                String nombre = consecutivo.toString()+".mp3";
                path = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+nombre;


                grabacion = new MediaRecorder();
                grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                grabacion.setOutputFile(path);
                try{
                    grabacion.prepare();
                    grabacion.start();
                } catch (IOException e){
                }

                btn_recorder.setBackgroundResource(R.drawable.rec);
                Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
            } else if(grabacion!=null){
            grabacion.stop();
            grabacion.release();
            fileVideo = new File(path);
            grabacion = null;
            btn_recorder.setBackgroundResource(R.drawable.stop_rec);
            buttonEnviar.setEnabled(true);
            grabado = true;
            Toast.makeText(getApplicationContext(), "Grabación finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    public void reproducir(){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        }catch (IOException e){
        }

        mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproducir audio", Toast.LENGTH_SHORT).show();

    }


    public void EnviarAudio(){

        Context context = getApplicationContext();
        CharSequence text = "Enviando Audio";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        try {
            byte[] arreglo_binarios = FileUtils.readFileToByteArray(fileVideo);//Convert any file, image or video into byte array
            ruta = Base64.encodeToString(arreglo_binarios, Base64.NO_WRAP);//Convert byte array into string
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        Fecha = dateFormat.format(date);

        ruta = "data:image/mp3;base64,"+ruta;


        SesionVocal sesionVocal = new SesionVocal(paciente_id, vocales_id, Aprobado, Fecha, ruta);

        apiClient.registroSesionVocales(sesionVocal).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                Log.i("Enviando audio", response.body().getStatus() + " " + response.body().getCode());
                String responseCode = response.body().getCode();

                switch (responseCode) {
                    case "200":
                        //goToPaginaPrincipal();
                        Context context = getApplicationContext();
                        CharSequence text = "Audio Enviado";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        buttonHistorialAudios.setEnabled(true);
                        buttonEnviar.setEnabled(false);
                        break;
                    case "400":
                        Toast.makeText(getApplicationContext(),
                                "Ocurrió un problema, no se pudo enviar el audio",
                                Toast.LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.e("Enviando audio", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurriò un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }




    private void goToPaginaPrincipal() {
        Intent intent = new Intent(this, PaginaPrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToHistorialAudios(View view) {
        Intent intent = new Intent(this, HistorialAudiosFechas.class);
        intent.putExtra("vocal_id",vocales_id);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_VIDEO:
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                Context context = getApplicationContext();
                CharSequence text = "Termine de grabar";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                break;
        }

    }
}
