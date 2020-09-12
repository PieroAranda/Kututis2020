package com.example.kututistesis.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.api.ApiClient;
import com.example.kututistesis.model.ArchivoSesionPraxia;
import com.example.kututistesis.model.SesionPraxia;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialVideosFechasAdapter extends RecyclerView.Adapter<HistorialVideosFechasAdapter.MyViewHolder> {

    private List<ArchivoSesionPraxia> sesionPraxiaList;
    private Context context;
    private VideoView video;
    private MediaController mediaController;

    private static final String CARPETA_PRINCIPAL_VIDEO_HISTORIAL = "misVideosHistorialApp/";
    private static final String CARPETA_HISTORIAL = "videosHistorial";
    private static final String DIRECTORIO_VIDEOHISTORIAL = CARPETA_PRINCIPAL_VIDEO_HISTORIAL + CARPETA_HISTORIAL;

    private String pathVideoHistorial;
    File fileVideoHistorial;

    public HistorialVideosFechasAdapter() {

    }

    public void setData(List<ArchivoSesionPraxia> sesionPraxiaList, VideoView video, MediaController mediaController) {
        this.sesionPraxiaList= sesionPraxiaList;
        this.video = video;
        this.mediaController = mediaController;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new HistorialVideosFechasAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_videos_por_fecha,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ArchivoSesionPraxia sesionPraxia = sesionPraxiaList.get(position);
        Integer intento = sesionPraxiaList.size() - position;
        final String numero_intento = "Intento #"+intento;
        holder.intento.setText(numero_intento);
        String aprobado;
        if(sesionPraxia.getAprobado() == 0 || sesionPraxia.getAprobado() == 1)
        {
            aprobado = "Intentar Nuevamente";
        }
        else
            aprobado = "Aprobado";
        holder.aprobado.setText(aprobado);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarVideoHistorial(sesionPraxia.getArchivo(), holder.play);
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


    public void TomarVideoHistorial(String archivo, @NonNull final ImageView play){

        File directorioVideos = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_VIDEOHISTORIAL);

        if(!directorioVideos.exists()) {
            directorioVideos.mkdirs();
        } else {
            Long timestamp = System.currentTimeMillis() / 1000;
            String fileName = timestamp + ".mp4";

            pathVideoHistorial = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_VIDEOHISTORIAL
                    + File.separator + fileName;

            fileVideoHistorial = new File(pathVideoHistorial);
            byte[] bytearray = Base64.decode(archivo, Base64.DEFAULT);
            File file = convertirByteArrayIntoFile(bytearray, fileVideoHistorial);

            playVideo(file, play);
        }
    }


    public void playVideo(File file, @NonNull final ImageView play) {
        try {
            if (!video.isPlaying()){



                video.setVideoPath(file.getAbsolutePath());


                video.setMediaController(mediaController);
                mediaController.setAnchorView(video);

                video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                });
            }else {
                video.pause();
                play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }

        }catch (Exception e)
        {

        }
        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //mp.setLooping(true);
                video.start();
                play.setImageResource(R.drawable.ic_pause_black_24dp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sesionPraxiaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView intento;
        TextView aprobado;
        LinearLayout item;
        ImageView play;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            intento = itemView.findViewById(R.id.TituloVideo);
            aprobado = itemView.findViewById(R.id.Aprobado);
            play = itemView.findViewById(R.id.PlayPause);


        }
    }
}
