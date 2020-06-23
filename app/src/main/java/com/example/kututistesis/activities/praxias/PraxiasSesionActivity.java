package com.example.kututistesis.activities.praxias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.MenuPrincipalActivity;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.Praxia;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.util.Global;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PraxiasSesionActivity extends AppCompatActivity {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;
    File fileVideo;
    private ApiClient apiClient;
    private Integer paciente_id;
    private Integer Aprobado;
    private String Fecha;
    private String ruta;
    private VideoView videoEjemplo;
    private boolean enviando = false;
    private static final int COD_VIDEO = 20;
    private ImageView buttonhitorialVideos;
    private ImageView play;
    private Global global;
    private ImageView imageViewAtras;
    private Boolean reproducido = false;
    private Integer position = 0;
    private ImageView imageViewRecordVideo;
    private TextView textViewTitulo;
    private Praxia praxia;
    private ProgressBar progressBarVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_praxias_sesion);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        global = (Global) getApplicationContext();

        Intent intent = getIntent();
        praxia = (Praxia) intent.getSerializableExtra("praxia");
        Integer intent_praxia_id = intent.getIntExtra("praxia_id",0);
        String praxiaNombre = intent.getStringExtra("praxia_nombre");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (ContextCompat.checkSelfPermission(PraxiasSesionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PraxiasSesionActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PraxiasSesionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        apiClient = ApiClient.getInstance();
        paciente_id = global.getId_usuario();
        Aprobado = 0;
        Fecha = "";
        ruta = "";

        buttonhitorialVideos = findViewById(R.id.buttonHistorailVideos);
        videoEjemplo = findViewById(R.id.videoEjemploPraxia);
        imageViewRecordVideo = findViewById(R.id.imageView2);
        play = findViewById(R.id.playVideoEjemplo);
        imageViewAtras = findViewById(R.id.imageViewPraxiasAtras);
        textViewTitulo = findViewById(R.id.textViewPraxiasSesionTitulo);
        progressBarVideo = findViewById(R.id.progressBarPraxiasSesionVideo);

        loadTitle();
        loadVideo();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });

        imageViewRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarVideo();
            }
        });

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadVideo() {
        Uri uri = Uri.parse(praxia.getVideo());
        videoEjemplo.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoEjemplo.setMediaController(mediaController);
        mediaController.setAnchorView(videoEjemplo);

        videoEjemplo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                position = 0;
            }
        });
    }

    private void loadTitle() {
        textViewTitulo.setText(praxia.getNombre());
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    public void TomarVideo() {

        // Mientras está enviando un video no abre la cámara
        if(enviando) {
            return;
        }

        File directorioVideos = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);

        if(!directorioVideos.exists()) {
            directorioVideos.mkdirs();
        } else {
            Long timestamp = System.currentTimeMillis() / 1000;
            String fileName = timestamp.toString() + ".mp4";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + fileName;

            fileVideo = new File(path);

            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileVideo));

            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, COD_VIDEO);
            }
        }
    }

    public void playVideo() {
        try {
            if (!videoEjemplo.isPlaying()){
                if(!reproducido){
                    reproducido = true;
                }else {
                    videoEjemplo.seekTo(position);
                }
                videoEjemplo.start();
                play.setImageResource(R.drawable.boton_parar);
            }else {
                position = videoEjemplo.getCurrentPosition();
                videoEjemplo.pause();
                play.setImageResource(R.drawable.boton_reproducir);
            }

        }catch (Exception e)
        {

        }
        videoEjemplo.requestFocus();
    }

    public void EnviarVideo() {

        enviando = true;

        try {
            byte[] arreglo_binarios = FileUtils.readFileToByteArray(fileVideo);//Convert any file, image or video into byte array
            Log.i("VIDEO_SIZE", arreglo_binarios.length + "");
            ruta = Base64.encodeToString(arreglo_binarios, Base64.NO_WRAP);//Convert byte array into string
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        Fecha = dateFormat.format(date);

        ruta = "data:image/mp4;base64,"+ruta;

        SesionPraxia sesionPraxia = new SesionPraxia(paciente_id, praxia.getId(), Aprobado, Fecha, ruta);

        apiClient.registroSesionPraxias(sesionPraxia).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                enviando = false;
                progressBarVideo.setVisibility(View.GONE);
                Log.i("VIDEO", response.toString());
                Context context = getApplicationContext();
                CharSequence text = "Video Enviado";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                buttonhitorialVideos.setEnabled(true);
                //goToPaginaPrincipal();
                //Log.i("Enviando video", response.body().getStatus() + " " + response.body().getCode());
                //String responseCode = response.body().getCode();

                /*switch (responseCode) {
                    case "200":
                        goToPaginaPrincipal();
                        break;
                    case "400":
                        Toast.makeText(getApplicationContext(),
                                "Ocurrío un problema, no se pudo enviar el video",
                                Toast.LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        break;
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                enviando = false;
                progressBarVideo.setVisibility(View.GONE);
                Log.e("Enviando video", t.getMessage());
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

    public void goToHistorialVideos(View view) {
        Intent intent = new Intent(this, PraxiasHistorialActivity.class);
        intent.putExtra("praxia_id", praxia.getId());
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Evita que continue el guardado si el usuario cerró la cámara sin grabar
        if (resultCode == 0) {
            return;
        }

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
                CharSequence text = "Enviando el video";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                progressBarVideo.setVisibility(View.VISIBLE);
                // Habilita el botón de enviar
                //buttonEnviar.setEnabled(true);
                EnviarVideo();
                break;
        }

    }
}
