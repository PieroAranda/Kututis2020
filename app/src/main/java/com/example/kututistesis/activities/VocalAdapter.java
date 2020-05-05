package com.example.kututistesis.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.Praxias;
import com.example.kututistesis.model.Vocales;

import java.util.List;

public class VocalAdapter extends RecyclerView.Adapter<VocalAdapter.MyViewHolder> {
    private List<Vocales> vocalesList;
    private Context context;

    public VocalAdapter() {

    }

    public void setData(List<Vocales> vocalesList) {
        this.vocalesList = vocalesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new VocalAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_vocales,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vocales vocales = vocalesList.get(position);
        String nombreVocal = vocales.getNombre();

        holder.NombreVocal.setText(nombreVocal);
    }

    @Override
    public int getItemCount() {
        return vocalesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView NombreVocal;
        ImageView nextVocal;
        LinearLayout item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            NombreVocal = itemView.findViewById(R.id.nombreVocal);
            item = itemView.findViewById(R.id.item_fonema_vocalico);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SesionVocalGrabarActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
