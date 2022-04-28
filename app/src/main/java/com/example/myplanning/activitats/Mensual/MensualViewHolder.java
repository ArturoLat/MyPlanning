package com.example.myplanning.activitats.Mensual;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.myplanning.R;

public class MensualViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ArrayList<LocalDate> dies;
    public final View parentView;
    public final TextView diaMes;
    private final MensualAdapter.onItemListener onItemListener;

    public MensualViewHolder(@NonNull View itemView, MensualAdapter.onItemListener onItemListener, ArrayList<LocalDate> dies) {
        super(itemView);
        this.dies = dies;
        this.diaMes = itemView.findViewById(R.id.cellDiaText);
        this.onItemListener = onItemListener;
        this.parentView = itemView.findViewById(R.id.parentView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), dies.get(getAdapterPosition()));
    }
}
