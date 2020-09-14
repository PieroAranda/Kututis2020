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
import com.example.kututistesis.model.SesionVocabulario;
import com.example.kututistesis.model.Vocabulario;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.util.List;

public class VocabularioAdapter extends RecyclerView.Adapter<VocabularioAdapter.MyViewHolder> {

    private List<SesionVocabulario> vocabularioList;
    private Context context;
    private OnVocabularioListener mOnvocabulariolistener;

    public VocabularioAdapter(){

    }

    public void setData(List<SesionVocabulario> vocabularioList, OnVocabularioListener onVocabularioListener){
        this.vocabularioList = vocabularioList;
        this.mOnvocabulariolistener = onVocabularioListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new VocabularioAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_vocabulario,parent,false), mOnvocabulariolistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SesionVocabulario vocabulario = vocabularioList.get(position);
        holder.textoPalabra.setText(vocabulario.getVocabulario().getPalabra());

        byte[] bytearray = Base64.decode(vocabulario.getVocabulario().getImagen(), Base64.DEFAULT);

        ByteArrayInputStream imageStream = new ByteArrayInputStream(bytearray);
        Bitmap imagen = BitmapFactory.decodeStream(imageStream);

        holder.imagenPalabra.setImageBitmap(imagen);

        /*Picasso.get().load(vocabulario.getImagen()).into(holder.imagenPalabra);*/
    }

    @Override
    public int getItemCount() {
        return vocabularioList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagenPalabra;
        TextView textoPalabra;
        OnVocabularioListener onVocabularioListener;

        public MyViewHolder(@NonNull View itemView, OnVocabularioListener onVocabularioListener) {
            super(itemView);
            imagenPalabra = itemView.findViewById(R.id.ImagenPalabra);
            textoPalabra = itemView.findViewById(R.id.TextoPalabra);

            this.onVocabularioListener = onVocabularioListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVocabularioListener.onVocabularioClick(getAdapterPosition());
        }
    }

    public interface OnVocabularioListener{
        void onVocabularioClick(int position);
    }
}
