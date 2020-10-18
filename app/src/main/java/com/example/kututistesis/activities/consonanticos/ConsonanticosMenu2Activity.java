package com.example.kututistesis.activities.consonanticos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.MenuLogros;
import com.example.kututistesis.adapters.VocabularioAdapter;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.PacienteLogro;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionVocabulario;
import com.example.kututistesis.model.Vocabulario;
import com.example.kututistesis.util.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsonanticosMenu2Activity extends AppCompatActivity implements VocabularioAdapter.OnVocabularioListener{

    private ApiClient apiClient;
    private String url;

    private RecyclerView recyclerView;
    private VocabularioAdapter vocabularioAdapter;
    private ProgressBar progressBarMenu;
    private TextView vocabularioText;
    private TextView vocabularioTextWords;


    private ImageView imageViewAtras;

    private List<SesionVocabulario> sesionVocabulario;


    private Global global;
    private Integer paciente_id;

    private List<PacienteLogro> pacienteLogroList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consonanticos_menu_2);

        // Cambia el color de la barra de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        apiClient = ApiClient.getInstance();
        url = ApiClient.BASE_STORAGE_IMAGE_URL;
        recyclerView = findViewById(R.id.recyclerVocabulario);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageViewAtras = findViewById(R.id.imageViewVocabularioAtras);
        progressBarMenu = findViewById(R.id.progressBarConsonanticosMenu2);
        vocabularioText = findViewById(R.id.vocabularioText);
        vocabularioTextWords = findViewById(R.id.textView2);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsonanticosMenu2Activity.super.onBackPressed();
            }
        });

        vocabularioAdapter = new VocabularioAdapter();

        /*Intent intent = getIntent();

        fonema_id = intent.getIntExtra("consonante_id",0);*/
        global = (Global) getApplicationContext();

        paciente_id = global.getId_usuario();

        /*obtenerVocabulario(paciente_id);*/
    }

    public void visualizarLogroPrimeraLeccion(Integer logro_id){

        apiClient.agregar_logro(paciente_id, logro_id).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.body().getCode().equals("200")){
                    Log.e("Logro PrimeraLeccion", "Logro Primera Leccion");
                    //Toast.makeText(getApplicationContext(),
                    //        "Logro conseguido: Primera Lección",
                    //        Toast.LENGTH_SHORT)
                    //        .show();
                    showNotification("Primera lección");
                    showAlertDialog("Logro conseguido", "Primera lección");
                }else {
                    Log.e("No Logro PrimeraLeccion", "No Logro Primera Leccion");
                    Toast.makeText(getApplicationContext(),
                            "No se pudo agregar logro",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.e("ObteniendoPacienteLogro", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void checkifobtuvoLogroPrimeraLeccion(){
        apiClient.listarlogroxusuarioid(paciente_id).enqueue(new Callback<List<PacienteLogro>>() {
            @Override
            public void onResponse(Call<List<PacienteLogro>> call, Response<List<PacienteLogro>> response) {
                pacienteLogroList = response.body();

                if(!pacienteLogroList.isEmpty()){
                    for(PacienteLogro pl:pacienteLogroList){
                        if(pl.getLogro_id() == 1){
                            return;
                        }
                    }
                    visualizarLogroPrimeraLeccion(1);
                    return;
                }else {
                    visualizarLogroPrimeraLeccion(1);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<PacienteLogro>> call, Throwable t) {
                Log.e("ObteniendoPacienteLogro", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void obtenerVocabulario(Integer paciente_id){
        progressBarMenu.setVisibility(View.VISIBLE);

        apiClient.listar_sesionvocabularioxusuario(paciente_id).enqueue(new Callback<List<SesionVocabulario>>() {
            @Override
            public void onResponse(Call<List<SesionVocabulario>> call, Response<List<SesionVocabulario>> response) {
                progressBarMenu.setVisibility(View.GONE);
                sesionVocabulario = response.body();
                Log.e("sesionVocabulario ",sesionVocabulario.toString());

                for(SesionVocabulario sv:sesionVocabulario){
                    if(sv.getCompletado()==1){
                        checkifobtuvoLogroPrimeraLeccion();
                    }
                }
                //si no tiene sesiones
                if(sesionVocabulario.toString() == "[]"){
                   vocabularioTextWords.setVisibility(View.GONE);
                   recyclerView.setVisibility(View.GONE);
                   vocabularioText.setVisibility(View.VISIBLE);
                }

                vocabularioAdapter.setData(sesionVocabulario, ConsonanticosMenu2Activity.this);
                recyclerView.setAdapter(vocabularioAdapter);

            }

            @Override
            public void onFailure(Call<List<SesionVocabulario>> call, Throwable t) {
                Log.d("FalloSesionPraxia", "Trowable"+t);
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se pudo obtener el vocabulario",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        /*apiClient.buscarvocabularioxfonemaid(fonema_id).enqueue(new Callback<List<Vocabulario>>() {
            @Override
            public void onResponse(Call<List<Vocabulario>> call, Response<List<Vocabulario>> response) {
                progressBarMenu.setVisibility(View.GONE);

                vocabularioList = response.body();
                for(Vocabulario vocabulario:vocabularioList)
                {
                    url = url + vocabulario.getImagen();
                    vocabulario.setImagen(url);
                    url = ApiClient.BASE_STORAGE_IMAGE_URL;
                }
                vocabularioAdapter.setData(vocabularioList, ConsonanticosMenu2Activity.this);
                recyclerView.setAdapter(vocabularioAdapter);
            }

            @Override
            public void onFailure(Call<List<Vocabulario>> call, Throwable t) {
                progressBarMenu.setVisibility(View.GONE);

                Log.e("Obteniendo vocabulario", t.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ocurrió un problema, no se puede conectar al servicio",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });*/
    }

    @Override
    public void onVocabularioClick(int position) {
        String imagen = sesionVocabulario.get(position).getVocabulario().getImagen();
        String palabra = sesionVocabulario.get(position).getVocabulario().getPalabra();
        Integer id_fonema = sesionVocabulario.get(position).getVocabulario().getFonema_id();
        Integer id_sesion_vocabulario = sesionVocabulario.get(position).getId();
        Integer repeticiones = sesionVocabulario.get(position).getRepeticiones();
        Intent intent = new Intent(this, ConsonanticosSesionActivity.class);
        intent.putExtra("imagen_palabra", imagen);
        intent.putExtra("texto_palabra", palabra);
        intent.putExtra("consonante_id", id_fonema);
        intent.putExtra("id_sesion_vocabulario", id_sesion_vocabulario);
        intent.putExtra("repeticiones", repeticiones);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerVocabulario(paciente_id);
    }

    public void showNotification(String message){
        int NOTIFICATION_ID = 234;
        String CHANNEL_ID = "hola";
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.trophy)
                .setContentTitle("Logro conseguido")
                .setContentText(message);

        Intent resultIntent = new Intent(this, MenuLogros.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MenuLogros.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
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
}
