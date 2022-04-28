package com.example.myplanning.activitats.Mensual;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.R;


public class MensualAdapter extends RecyclerView.Adapter<MensualViewHolder> {

    /*public final View parentView;*/
    private final ArrayList<LocalDate> diesMes;
    private final onItemListener onItemListener;

    public MensualAdapter(ArrayList<LocalDate> diesMes, MensualAdapter.onItemListener onItemListener) {
        this.diesMes = diesMes;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public MensualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendari_dia, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new MensualViewHolder(view, onItemListener,this.diesMes);
    }

    @Override
    public void onBindViewHolder(@NonNull MensualViewHolder holder, int position) {
        final LocalDate date = this.diesMes.get(position);

        holder.diaMes.setText(String.valueOf(date.getDayOfMonth()));

        if(date.equals(CalendariUtiles.selectedDate))
            holder.parentView.setBackgroundColor(Color.LTGRAY);

        if(date.getMonth().equals(CalendariUtiles.selectedDate.getMonth()))
            holder.diaMes.setTextColor(Color.BLACK);
        else
            holder.diaMes.setTextColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return diesMes.size();
    }

    public interface onItemListener{
        void onItemClick(int pos, LocalDate date);
    }
}
