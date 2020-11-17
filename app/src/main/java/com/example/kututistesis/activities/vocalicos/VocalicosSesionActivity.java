package com.example.kututistesis.activities.vocalicos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.MenuPrincipalActivity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ArchivoSesionFonema;
import com.example.kututistesis.model.Fonema;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionFonema;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.model.Vocal;
import com.example.kututistesis.util.Global;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VocalicosSesionActivity extends Activity {
    private MediaRecorder grabacion;

    private static final String CARPETA_PRINCIPAL = "misAudiosApp/";
    private static final String CARPETA_AUDIO = "audios";
    private static final String DIRECTORIO_AUDIO = CARPETA_PRINCIPAL + CARPETA_AUDIO;
    private String path;
    File fileVideo;
    private ApiClient apiClient;

    private Integer paciente_id;
    private Integer Aprobado;
    private String Fecha;
    private String ruta;
    private SesionFonema sesionFonema;

    private static final int COD_VIDEO = 20;
    private Button buttonEnviar;
    private Button buttonReproducir;
    private Button buttonGrabar;
    private Boolean grabado = false;
    private ImageView buttonHistorialAudios;
    private Global global;
    private ImageView imageViewAtras;
    private TextView textViewFonema;
    private int screenHeight;

    private Integer id_sesion_fonema;
    private Fonema fonema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_vocalicos_sesion);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        // Obtiene el largo de la pantalla para mostrar correctamente los Toast
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;

        global = (Global) getApplicationContext();

        Intent intent = getIntent();
        /*sesionFonema = (SesionFonema) intent.getSerializableExtra("sesion_fonema");*/
        Integer id_fonema =  intent.getIntExtra("id_fonema",0);
        id_sesion_fonema = intent.getIntExtra("id_sesion_fonema", 0);

        apiClient = ApiClient.getInstance();

        paciente_id = global.getId_usuario();
        Aprobado = 0;
        Fecha = "";
        ruta = "";

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) { //estrutura condicional que revisa si se ha puesto los permisos para grabar audio y escribir en el celular los audios, en el archivo manifest
            ActivityCompat.requestPermissions(VocalicosSesionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000); //escribe en pantalla las ventanas empergentes que piden permisos para acceder al microfno y que se guarden los audios en el celular
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) { //estrutura condicional que revisa si se ha puesto los permisos para grabar audio y escribir en el celular los audios, en el archivo manifest
            ActivityCompat.requestPermissions(VocalicosSesionActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1000); //escribe en pantalla las ventanas empergentes que piden permisos para acceder al microfno y que se guarden los audios en el celular
        }

        buttonGrabar = (Button) findViewById(R.id.btn_rec);
        buttonEnviar = (Button) findViewById(R.id.buttonEnvio2);
        buttonReproducir = (Button) findViewById(R.id.btn_play);
        imageViewAtras = findViewById(R.id.imageViewVocalicosSesionAtras);
        textViewFonema = findViewById(R.id.textViewVocalicosSesionFonema);
        buttonHistorialAudios = findViewById(R.id.buttonHistorialAudios);

        loadFonema(id_fonema);

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

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recorder();
            }
        });

        buttonHistorialAudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                goToHistorialAudios();
            }
        });
    }

    private void loadFonema(Integer id_fonema) {
        apiClient.buscarfonemaxid(id_fonema).enqueue(new Callback<List<Fonema>>() {
            @Override
            public void onResponse(Call<List<Fonema>> call, Response<List<Fonema>> response) {
                fonema = response.body().get(0);
                textViewFonema.setText(fonema.getNombre());
            }

            @Override
            public void onFailure(Call<List<Fonema>> call, Throwable t) {
                Log.d("FalloSesionPraxia", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener el fonema",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void Recorder() {
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
                    grabacion.setMaxDuration(60000);
                    grabacion.prepare();
                    grabacion.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                        @Override
                        public void onInfo(MediaRecorder mr, int what, int extra) {
                            if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
                                try {
                                    grabacion.stop();
                                } catch (Exception e) {

                                }
                                grabacion.release();
                                fileVideo = new File(path);
                                grabacion = null;
                                buttonGrabar.setBackgroundResource(R.drawable.boton_grabar);
                                buttonEnviar.setEnabled(true);
                                grabado = true;
                                Toast t = Toast.makeText(getApplicationContext(), "Grabación finalizada", Toast.LENGTH_SHORT);
                                t.setGravity(Gravity.BOTTOM, 0, (screenHeight / 100) * 25);
                                t.show();
                                buttonReproducir.setBackgroundResource(R.drawable.boton_reproducir);
                                buttonEnviar.setBackgroundResource(R.drawable.boton_enviar);
                            }
                        }
                    });
                    grabacion.start();
                } catch (IOException e){
                }

            buttonGrabar.setBackgroundResource(R.drawable.boton_parar_grabacion);
                Toast t = Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.BOTTOM, 0, (screenHeight / 100) * 25);
                t.show();
            } else if(grabacion!=null){
            try {
                grabacion.stop();
            } catch (Exception e) {

            }
            grabacion.release();
            fileVideo = new File(path);
            grabacion = null;
            buttonGrabar.setBackgroundResource(R.drawable.boton_grabar);
            buttonEnviar.setEnabled(true);
            grabado = true;
            Toast t = Toast.makeText(getApplicationContext(), "Grabación finalizada", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.BOTTOM, 0, (screenHeight / 100) * 25);
            t.show();
            buttonReproducir.setBackgroundResource(R.drawable.boton_reproducir);
            buttonEnviar.setBackgroundResource(R.drawable.boton_enviar);
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
        Toast t = Toast.makeText(getApplicationContext(), "Reproducir audio", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.BOTTOM, 0, (screenHeight / 100) * 25);
        t.show();

    }

    public void EnviarAudio(){
        Context context = getApplicationContext();
        CharSequence text = "Enviando Audio";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM, 0, (screenHeight / 100) * 25);
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


        apiClient.registroArchivoSesionFonemas(id_sesion_fonema, Fecha, ruta).enqueue(new Callback<ResponseStatus>() {
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
                        toast.setGravity(Gravity.BOTTOM, 0, (screenHeight / 100) * 25);
                        toast.show();
                        buttonEnviar.setEnabled(false);
                        buttonEnviar.setBackgroundResource(R.drawable.boton_enviar_deshabilitado);
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

    public void goToHistorialAudios() {
        Intent intent = new Intent(this, VocalicosHistorialActivity.class);
        intent.putExtra("sesion_fonema_id", id_sesion_fonema);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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

    @Override
    public void onBackPressed()
    {
        stopRecording();
        super.onBackPressed();  //if you want to do something new remove this line
    }

    public void stopRecording() {
        grabado = true;
        buttonGrabar.setBackgroundResource(R.drawable.boton_grabar);
        buttonReproducir.setBackgroundResource(R.drawable.boton_reproducir_deshabilitado);
        buttonEnviar.setBackgroundResource(R.drawable.boton_enviar_deshabilitado);
        if (grabacion != null) {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
        }
    }
}
