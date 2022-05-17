package com.example.myplanning.activitats.Diari;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanning.R;
import com.example.myplanning.model.Item.Dades;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderDiari> {

    private ArrayList<Object> listdatos;

    public AdapterRecycler(ArrayList<Object> listdatos) {
        if(!listdatos.isEmpty()){
            this.listdatos = listdatos;

        }else{
            this.listdatos = new ArrayList<>();

        }

    }

    public interface changeAdapterInterface{
        void changeAdapterSchedule();
        void changeAdapterToDo();
        void changeAdapterHomework();
    }

    @NonNull
    @Override
    public AdapterRecycler.ViewHolderDiari onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_diari,parent,false);
        return new ViewHolderDiari(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDiari holder, int position) {

        Dades dada = (Dades) listdatos.get(position);

        holder.check.setText(dada.toString());

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
