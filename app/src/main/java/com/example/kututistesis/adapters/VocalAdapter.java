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
import com.example.kututistesis.model.Vocal;

import java.util.List;

public class VocalAdapter extends RecyclerView.Adapter<VocalAdapter.MyViewHolder> {
    private List<Vocal> vocalesList;
    private Context context;
    private OnVocalesListener mOnVocalesListener;

    public VocalAdapter() {

    }

    public void setData(List<Vocal> vocalesList, OnVocalesListener mOnVocalesListener) {
        this.vocalesList = vocalesList;
        this.mOnVocalesListener = mOnVocalesListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new VocalAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_vocales,parent,false), mOnVocalesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vocal vocales = vocalesList.get(position);
        String nombreVocal = vocales.getNombre();

        holder.NombreVocal.setText(nombreVocal);
    }

    @Override
    public int getItemCount() {
        return vocalesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView NombreVocal;
        ImageView nextVocal;
        OnVocalesListener onVocalesListener;

        public MyViewHolder(@NonNull View itemView, OnVocalesListener onVocalesListener) {
            super(itemView);
            NombreVocal = itemView.findViewById(R.id.nombreVocal);

            this.onVocalesListener = onVocalesListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onVocalesListener.OnVocalClick(getAdapterPosition());
        }
    }

    public interface OnVocalesListener{
        void OnVocalClick(int position);
    }
}
