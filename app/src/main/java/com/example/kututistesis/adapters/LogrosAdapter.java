package com.example.kututistesis.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.PacienteLogro;
import com.example.kututistesis.model.SesionVocabulario;

import java.io.ByteArrayInputStream;
import java.util.List;

public class LogrosAdapter extends RecyclerView.Adapter<LogrosAdapter.MyViewHolder> {

    private List<PacienteLogro> pacienteLogroList;
    private Context context;
    private OnLogrosListener monLogrosListener;

    public void setData(List<PacienteLogro> pacienteLogroList, OnLogrosListener monLogrosListener){
        this.pacienteLogroList = pacienteLogroList;
        this.monLogrosListener = monLogrosListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LogrosAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_logros,parent,false), monLogrosListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PacienteLogro pacienteLogro = pacienteLogroList.get(position);
        holder.NombreLogro.setText(pacienteLogro.getLogro().getNombre());
        holder.DescripcionLogro.setText(pacienteLogro.getLogro().getDescripcion());

        /*byte[] bytearray = Base64.decode(vocabulario.getVocabulario().getImagen(), Base64.DEFAULT);

        ByteArrayInputStream imageStream = new ByteArrayInputStream(bytearray);
        Bitmap imagen = BitmapFactory.decodeStream(imageStream);

        holder.imagenPalabra.setImageBitmap(imagen);*/


    }

    @Override
    public int getItemCount() {
        return pacienteLogroList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView NombreLogro;
        TextView DescripcionLogro;
        OnLogrosListener onLogrosListener;

        public MyViewHolder(@NonNull View itemView, OnLogrosListener onLogrosListener) {
            super(itemView);
            NombreLogro = itemView.findViewById(R.id.NombreLogro);
            DescripcionLogro = itemView.findViewById(R.id.DescripcionLogro);

            this.onLogrosListener = onLogrosListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onLogrosListener.onLogrosClick(getAdapterPosition());
        }
    }

    public interface OnLogrosListener{
        void onLogrosClick(int position);
    }
}
