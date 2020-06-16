package com.example.kututistesis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kututistesis.R;
import com.example.kututistesis.activities.consonanticos.MenuConsonanticosActivity;
import com.example.kututistesis.model.Banderin;
import com.example.kututistesis.model.Fonema;

import java.util.ArrayList;
import java.util.List;

public class MenuBanderaAdapter extends RecyclerView.Adapter<MenuBanderaAdapter.MyViewHolder> {

    private List<ConsonantesAdapter> banderasAdapters;
    private List<Banderin> fonemaList;
    private Context context;
    private ConsonantesAdapter.OnConsonantesListener mOnconsonanteslistener;

    public void setData(List<Banderin> fonemaList, ConsonantesAdapter.OnConsonantesListener onConsonantesListener) {
        int rows = (int) Math.ceil(fonemaList.size() / 6.0);

        banderasAdapters = new ArrayList<ConsonantesAdapter>();

        for (int i = 0; i < rows; i++) {
            ConsonantesAdapter adapter = new ConsonantesAdapter();
            if (fonemaList.size() - 5 * i > 6) {
                adapter.setData(fonemaList.subList(5 * i, 5 * i + 5), onConsonantesListener, i);
            } else {
                adapter.setData(fonemaList.subList(5 * i, fonemaList.size()), onConsonantesListener, i);
            }
            banderasAdapters.add(adapter);
        }

        this.fonemaList = fonemaList;
        this.mOnconsonanteslistener = onConsonantesListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuBanderaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MenuBanderaAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.banderas_adapter, parent, false), mOnconsonanteslistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuBanderaAdapter.MyViewHolder holder, int position) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
        );
        holder.row.setLayoutManager(layoutManager);
        holder.row.setItemAnimator(new DefaultItemAnimator());
        holder.row.setAdapter(banderasAdapters.get(position));
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(fonemaList.size() / 6.0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerView row;
        ConsonantesAdapter.OnConsonantesListener onConsonantesListener;

        public MyViewHolder(@NonNull View itemView, ConsonantesAdapter.OnConsonantesListener onConsonantesListener) {
            super(itemView);
            this.onConsonantesListener = onConsonantesListener;
            row = itemView.findViewById(R.id.recyclerViewBanderas);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //onConsonantesListener.onConsonanteClick(getAdapterPosition());
        }
    }

    public interface OnConsonantesListener {
        void onConsonanteClick(int position);
    }
}
