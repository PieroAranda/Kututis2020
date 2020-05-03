package com.example.kututistesis.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionPraxia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionPraxiaGrabarActivity extends AppCompatActivity {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;
    File fileVideo;
    Bitmap bitmap;
    private ApiClient apiClient;

    private Integer paciente_id;
    private Integer praxias_id;
    private Integer Aprobado;
    private String Fecha;
    private String ruta;

    private static final int COD_VIDEO = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_praxia_grabar);

        if (ContextCompat.checkSelfPermission(SesionPraxiaGrabarActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SesionPraxiaGrabarActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SesionPraxiaGrabarActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        apiClient = ApiClient.getInstance();

        paciente_id = 1;
        praxias_id = 1;
        Aprobado = 0;
        Fecha = "";
        ruta = "";
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    public void TomarVideo(View view) {
        File miFile = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if(isCreada==false) {
            isCreada=miFile.mkdirs();
        }
        if(isCreada==true){
            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre = consecutivo.toString()+".mp4";

            path = Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;

            fileVideo = new File(path);

            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileVideo));
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, COD_VIDEO);

            }

        }

    }

    public void EnviarVideo(View view){

        try {
            byte[] arreglo_binarios = FileUtils.readFileToByteArray(fileVideo);//Convert any file, image or video into byte array
            ruta = Base64.encodeToString(arreglo_binarios, Base64.NO_WRAP);//Convert byte array into string
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        Fecha = dateFormat.format(date);

        ruta = "data:image/mp4;base64,"+ruta;


        SesionPraxia sesionPraxia = new SesionPraxia(paciente_id, praxias_id, Aprobado, Fecha, ruta);

        apiClient.registroSesionPraxias(sesionPraxia).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                Log.i("Enviando video", response.body().getStatus() + " " + response.body().getCode());
                String responseCode = response.body().getCode();

                switch (responseCode) {
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
        Intent intent = new Intent(this, PaginaPrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
