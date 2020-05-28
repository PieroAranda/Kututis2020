package com.example.kututistesis.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.Fonema;

import java.util.List;

public class ConsonantesAdapter extends RecyclerView.Adapter<ConsonantesAdapter.MyViewHolder> {

    private List<Fonema> fonemaList;
    private Context context;

    public ConsonantesAdapter(){

    }

    public void setData(List<Fonema> fonemaList){
        this.fonemaList = fonemaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ConsonantesAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.column_consonante,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Fonema fonema = fonemaList.get(position);
        String nombre_fonema = fonema.getFonema();
        holder.textConsonante.setText(nombre_fonema);
    }

    @Override
    public int getItemCount() {
        return fonemaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textConsonante;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textConsonante = itemView.findViewById(R.id.TextConsonante);
        }
    }
}
