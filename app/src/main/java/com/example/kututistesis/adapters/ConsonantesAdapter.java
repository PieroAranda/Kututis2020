package com.example.kututistesis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.Banderin;
import com.example.kututistesis.model.Fonema;

import java.util.List;

public class ConsonantesAdapter extends RecyclerView.Adapter<ConsonantesAdapter.MyViewHolder> {

    private List<Banderin> fonemaList;
    private Context context;
    private OnConsonantesListener mOnconsonanteslistener;
    int row;

    public void setData(List<Banderin> fonemaList, OnConsonantesListener onConsonantesListener, int row) {
        this.fonemaList = fonemaList;
        this.mOnconsonanteslistener = onConsonantesListener;
        this.row = row;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ConsonantesAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.column_consonante, parent, false), mOnconsonanteslistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Banderin fonema = fonemaList.get(position);
        String nombre_fonema = fonema.getTexto();
        holder.textConsonante.setText(nombre_fonema);
        holder.position = position;
        holder.row = row;
    }

    @Override
    public int getItemCount() {
        return fonemaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textConsonante;
        OnConsonantesListener onConsonantesListener;
        int position;
        int row;

        public MyViewHolder(@NonNull View itemView, OnConsonantesListener onConsonantesListener) {
            super(itemView);
            this.onConsonantesListener = onConsonantesListener;
            textConsonante = itemView.findViewById(R.id.TextConsonante);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onConsonantesListener.onConsonanteClick(5 * row + position);
        }
    }

    public interface OnConsonantesListener {
        void onConsonanteClick(int position);
    }
}
