package com.example.kututistesis.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ArchivoSesionFonema;
import com.example.kututistesis.model.SesionVocal;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialAudiosFechasAdapter extends RecyclerView.Adapter<HistorialAudiosFechasAdapter.MyViewHolder> {

    private List<ArchivoSesionFonema> archivoSesionFonemasList;
    private Context context;

    private static final String CARPETA_PRINCIPAL_AUDIO_HISTORIAL = "misAudiosHistorialApp/";
    private static final String CARPETA_HISTORIAL_AUDIO = "audiosHistorial";
    private static final String DIRECTORIO_AUDIOHISTORIAL = CARPETA_PRINCIPAL_AUDIO_HISTORIAL + CARPETA_HISTORIAL_AUDIO;

    private String pathAudioHistorial;
    File fileAudioHistorial;


    public HistorialAudiosFechasAdapter(){

    }

    public void setData(List<ArchivoSesionFonema> archivoSesionFonemasList){
        this.archivoSesionFonemasList = archivoSesionFonemasList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new HistorialAudiosFechasAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_audios_por_fecha,parent,false));
   }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ArchivoSesionFonema arSesionFonema = archivoSesionFonemasList.get(position);
        Integer intento = archivoSesionFonemasList.size() - position;
        final String numero_intento = "Intento #"+intento;
        holder.intentoAudio.setText(numero_intento);
        String aprobado;
        if(arSesionFonema.getAprobado() == 0 || arSesionFonema.getAprobado() == 1)
        {
            aprobado = "Intentar Nuevamente";
        }
        else
            aprobado = "Aprobado";
        holder.aprobadoAudio.setText(aprobado);

        holder.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarAudioHistorial(arSesionFonema.getArchivo(), holder.playAudio);
            }
        }
        );
    }

    private File convertirByteArrayIntoFile(byte[] bytearray, File file){
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytearray);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return file;
    }


    public void TomarAudioHistorial(String archivo, @NonNull final ImageView play){

        File directorioVideos = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_AUDIOHISTORIAL);

        if(!directorioVideos.exists()) {
            directorioVideos.mkdirs();
        } else {
            Long timestamp = System.currentTimeMillis() / 1000;
            String fileName = timestamp + ".mp4";

            pathAudioHistorial = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_AUDIOHISTORIAL
                    + File.separator + fileName;

            fileAudioHistorial = new File(pathAudioHistorial);
            byte[] bytearray = Base64.decode(archivo, Base64.DEFAULT);
            File file = convertirByteArrayIntoFile(bytearray, fileAudioHistorial);

            playAudio(file, play);
        }
    }

    public void playAudio(File file, @NonNull final ImageView play){
        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            //Toast.makeText(context, "Reproducir audio "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Reproducir audio", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
        }

        mediaPlayer.start();

    }

    @Override
    public int getItemCount() {
        return archivoSesionFonemasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView intentoAudio;
        TextView aprobadoAudio;
        ImageView playAudio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            intentoAudio = itemView.findViewById(R.id.TituloAudio);
            aprobadoAudio = itemView.findViewById(R.id.AprobadoAudio);
            playAudio = itemView.findViewById(R.id.PlayPauseAudio);
        }
    }
}
