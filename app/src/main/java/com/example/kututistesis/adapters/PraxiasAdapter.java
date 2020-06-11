package com.example.kututistesis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.util.Global;
import com.example.kututistesis.model.Praxias;
import com.squareup.picasso.Picasso;


import java.util.List;

public class PraxiasAdapter extends RecyclerView.Adapter<PraxiasAdapter.MyViewHolder> {
    private List<Praxias> praxiasList;
    private Context context;
    private Global global;
    private OnPraxiaListener mOnpraxialistener;

    public PraxiasAdapter() {

    }

    public void setData(List<Praxias> praxiasList, Global global, OnPraxiaListener onPraxiaListener) {
        this.praxiasList = praxiasList;
        this.global = global;
        this.mOnpraxialistener = onPraxiaListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new PraxiasAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_praxias,parent,false),mOnpraxialistener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Praxias praxias = praxiasList.get(position);
        String nombrePraxia = praxias.getNombre();
        String url = praxias.getImagen();

        holder.NombrePraxia.setText(nombrePraxia);
        Picasso.get().load(url).into(holder.imagen);
    }


    @Override
    public int getItemCount() {
        return praxiasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imagen;
        TextView NombrePraxia;
        ImageView next;
        OnPraxiaListener onPraxiaListener;

        public MyViewHolder(@NonNull View itemView, OnPraxiaListener onPraxiaListener) {
            super(itemView);
            imagen = itemView.findViewById(R.id.ImagenPraxia);
            NombrePraxia = itemView.findViewById(R.id.nombrePraxia);
            this.onPraxiaListener = onPraxiaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPraxiaListener.onPraxiaClick(getAdapterPosition());
        }
    }

    public interface OnPraxiaListener{
        void onPraxiaClick(int position);
    }
}
