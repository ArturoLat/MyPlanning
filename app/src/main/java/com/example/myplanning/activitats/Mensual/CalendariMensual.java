package com.example.myplanning.activitats.Mensual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.myplanning.activitats.CalendariUtiles;

import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.R;

public class CalendariMensual extends AppCompatActivity implements MensualAdapter.onItemListener{

    private TextView mesAnyText;
    private RecyclerView calendariRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari_mensual);
        initWidgets();
        CalendariUtiles.selectedDate = LocalDate.now();
        setMesView();
    }

    private void initWidgets() {
        calendariRecyclerView = findViewById(R.id.calendariRecyclerView);
        mesAnyText = findViewById(R.id.mesAnyTV);
    }

    private void setMesView() {
        mesAnyText.setText(CalendariUtiles.mesAnyFromDate(CalendariUtiles.selectedDate));
        ArrayList<LocalDate> diesMes = CalendariUtiles.diesMesArray(CalendariUtiles.selectedDate);

        MensualAdapter mensualAdapter = new MensualAdapter(diesMes,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendariRecyclerView.setLayoutManager(layoutManager);
        calendariRecyclerView.setAdapter(mensualAdapter);
    }


    @Override
    public void onItemClick(int pos, LocalDate date)
    {
        if(date != null)
        {
            CalendariUtiles.selectedDate = date;
            startActivity(new Intent(this, CalendariDiari.class));
        }
    }

    public void prevMesAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.minusMonths(1);
        setMesView();
    }

    public void nextMesAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.plusMonths(1);
        setMesView();
    }
}
