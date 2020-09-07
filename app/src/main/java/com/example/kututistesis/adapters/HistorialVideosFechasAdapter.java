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
    private File storageDir;

    public HistorialVideosFechasAdapter() {

    }

    public void setData(List<ArchivoSesionPraxia> sesionPraxiaList, VideoView video, MediaController mediaController, File storageDir) {
        this.sesionPraxiaList= sesionPraxiaList;
        this.video = video;
        this.mediaController = mediaController;
        this.storageDir = storageDir;
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
                playVideo(sesionPraxia.getArchivo(), holder.play, position, numero_intento);
            }
        }

        );
    }

    private File convertirByteArrayIntoFile(byte[] bytearray, Integer position, String numero_intento){
        String FILEPATH = ApiClient.BASE_STORAGE_IMAGE_URL+ "VideoEjemplo"+ sesionPraxiaList.get(position).getId() + "-"+ sesionPraxiaList.get(position).getSesion_praxia_id()+"-"+sesionPraxiaList.get(position).getFecha()+"-"+numero_intento +".mp4";
        File file = new File(FILEPATH);
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

    private File generateTemporaryFile(String filename) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        String tempFileName = dateFormat.format(date);

        File tempFile = File.createTempFile(
                tempFileName,       /* prefix     "20130318_010530" */
                filename,           /* filename   "video.3gp" */
                storageDir          /* directory  "/data/sdcard/..." */
        );

        return tempFile;
    }

    public void playVideo(String archivo, @NonNull final ImageView play, Integer position, String numero_intento) {
        try {
            if (!video.isPlaying()){
                byte[] bytearray = Base64.decode(archivo, Base64.DEFAULT);
                File file = convertirByteArrayIntoFile(bytearray, position, numero_intento);

                try {
                    // Copy file to temporary file in order to view it.
                    File temporaryFile = generateTemporaryFile(file.getName());
                    FileUtils.copyFile(file, temporaryFile);
                    video.setVideoPath(temporaryFile.getAbsolutePath());

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
