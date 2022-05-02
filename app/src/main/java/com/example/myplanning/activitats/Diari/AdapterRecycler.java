package com.example.myplanning.activitats.Diari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanning.R;

import java.util.ArrayList;
import java.util.Map;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderDiari> {

    Map<String,Object> listdatos;
    ArrayList<String> list;
    Context contest;

    /*public AdapterRecycler(Map<String, Object> listdatos) {
        this.listdatos = listdatos;
    }*/
    public AdapterRecycler(ArrayList<String> listdatos, Context contest) {
        this.list = listdatos;
        this.contest = contest;
    }

    @NonNull
    @Override
    public AdapterRecycler.ViewHolderDiari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contest)
                .inflate(R.layout.recycler_view_diari,parent,false);
        return new ViewHolderDiari(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDiari holder, int position) {
        holder.check.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderDiari extends RecyclerView.ViewHolder {

        CheckBox check;

        public ViewHolderDiari(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.recycler_check_box);

        }

    }
}
