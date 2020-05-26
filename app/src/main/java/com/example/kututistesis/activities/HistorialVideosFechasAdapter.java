package com.example.kututistesis.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
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
import com.example.kututistesis.model.Praxias;
import com.example.kututistesis.model.SesionPraxia;

import java.util.List;

public class HistorialVideosFechasAdapter extends RecyclerView.Adapter<HistorialVideosFechasAdapter.MyViewHolder> {

    private List<SesionPraxia> sesionPraxiaList;
    private Context context;
    private VideoView video;
    private MediaController mediaController;

    public HistorialVideosFechasAdapter() {

    }

    public void setData(List<SesionPraxia> sesionPraxiaList, VideoView video, MediaController mediaController) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SesionPraxia sesionPraxia = sesionPraxiaList.get(position);
        Integer intento = position + 1;
        String numero_intento = "Intento #"+intento;
        holder.intento.setText(numero_intento);
        String aprobado;
        if(sesionPraxia.getAprobado() == 0)
        {
            aprobado = "Intentar Nuevamente";
        }
        else
            aprobado = "Aprobado";
        holder.aprobado.setText(aprobado);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(sesionPraxia.getRuta_servidor(), holder.play);
            }
        }

        );
    }

    public void playVideo(String url, @NonNull final ImageView play) {
        try {
            if (!video.isPlaying()){
                Uri uri = Uri.parse(url);
                video.setVideoURI(uri);

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
                mp.setLooping(true);
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
