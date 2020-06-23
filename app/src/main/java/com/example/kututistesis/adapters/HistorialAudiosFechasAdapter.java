package com.example.kututistesis.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.SesionVocal;

import java.io.IOException;
import java.util.List;

public class HistorialAudiosFechasAdapter extends RecyclerView.Adapter<HistorialAudiosFechasAdapter.MyViewHolder> {

    private List<SesionVocal> sesionVocalList;
    private Context context;

    public HistorialAudiosFechasAdapter(){

    }

    public void setData(List<SesionVocal> sesionVocalList){
        this.sesionVocalList = sesionVocalList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new HistorialAudiosFechasAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_audios_por_fecha,parent,false));
   }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SesionVocal sesionVocal = sesionVocalList.get(position);
        Integer intento = sesionVocalList.size() - position;
        String numero_intento = "Intento #"+intento;
        holder.intentoAudio.setText(numero_intento);
        String aprobado;
        if(sesionVocal.getAprobado() == 0)
        {
            aprobado = "Intentar Nuevamente";
        }
        else
            aprobado = "Aprobado";
        holder.aprobadoAudio.setText(aprobado);

        holder.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(sesionVocal.getRuta_servidor(), holder.playAudio);
            }
        }
        );
    }

    public void playAudio(String url, @NonNull final ImageView play){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        }catch (IOException e){
        }

        mediaPlayer.start();
        Toast.makeText(context, "Reproducir audio "+url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return sesionVocalList.size();
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
