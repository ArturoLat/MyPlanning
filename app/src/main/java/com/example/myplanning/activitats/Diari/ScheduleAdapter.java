package com.example.myplanning.activitats.Diari;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanning.R;
import com.example.myplanning.model.Item.Dades;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolderDiari>{

    private ArrayList<Dades> listdatos;
    private String type;

    public ScheduleAdapter(ArrayList<Dades> listdatos, String typedada) {
        this.type = typedada;
        if(!listdatos.isEmpty()){
            this.listdatos = listdatos;

        }else{
            this.listdatos = new ArrayList<>();

        }

    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolderDiari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_diari,parent,false);
        return new ViewHolderDiari(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDiari holder, int position) {
        Dades dada = (Dades) listdatos.get(position);
        holder.check.setText(dada.toString());
        holder.check.setTextColor(dada.getColor());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), updateTarea.class);
                intent.putExtra("act", dada.getActivitat());
                intent.putExtra("type", type);
                intent.putExtra("time", dada.getDate().toString());
                intent.putExtra("color", dada.getColor());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdatos.size();
    }


    public class ViewHolderDiari extends RecyclerView.ViewHolder {

        public CheckBox check;

        public ViewHolderDiari(@NonNull View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.recycler_check_box);

        }

    }

}
