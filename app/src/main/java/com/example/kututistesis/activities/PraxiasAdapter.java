package com.example.kututistesis.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.model.Praxias;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PraxiasAdapter extends RecyclerView.Adapter<PraxiasAdapter.MyViewHolder> {
    private List<Praxias> praxiasList;
    private Context context;

    public PraxiasAdapter() {

    }

    public void setData(List<Praxias> praxiasList) {
        this.praxiasList = praxiasList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new PraxiasAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_praxias,parent,false));
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Praxias praxias = praxiasList.get(position);
        String nombrePraxia = praxias.getNombre();
        String url = praxias.getVideo();

        holder.NombrePraxia.setText(nombrePraxia);
        //Bitmap bitmap = getBitmapFromURL(url);
        //holder.imagen.setImageBitmap(bitmap);
        //Picasso.with(context).load(url).fit().centerInside().into(holder.imagen);
    }


    @Override
    public int getItemCount() {
        return praxiasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView NombrePraxia;
        ImageView next;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.ImagenPraxia);
            NombrePraxia = itemView.findViewById(R.id.nombrePraxia);
            next = itemView.findViewById(R.id.GrabarPraxia);

        }
    }
}
