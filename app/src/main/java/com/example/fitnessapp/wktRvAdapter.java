package com.example.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class wktRvAdapter extends RecyclerView.Adapter<wktRvAdapter.ViewHolder> {
    List<Weights> list;

    public wktRvAdapter(List<Weights> list) {
        this.list = list;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_wkt_rv,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Weights wts=list.get(position);

        holder.name.setText(wts.getName());
//        holder.dets.setText(wts.getimage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,dets;
        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_wktname);
            dets=itemView.findViewById(R.id.tv_wktDets);
        }
    }
}
