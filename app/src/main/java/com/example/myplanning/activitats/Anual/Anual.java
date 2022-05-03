package com.example.myplanning.activitats.Anual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanning.R;
import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.activitats.Mensual.CalendariMensual;
import com.example.myplanning.activitats.Mensual.MensualAdapter;
import com.example.myplanning.activitats.Seleccio.Seleccio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Anual extends AppCompatActivity {

    private Button btnAnterior;
    private Button btnTxt;
    private Button btnSeguent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anual_layout);
        initWidgets();
        setAnyView();
    }

    private void initWidgets(){
        btnAnterior = findViewById(R.id.btnAnteriorAnual);
        btnTxt = findViewById(R.id.btnAny);
        btnSeguent = findViewById(R.id.btnSeguentAnual);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void txtAccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }

    public void prevAnyAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.minusYears(1);
        setAnyView();
    }

    public void nextAnyAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.plusYears(1);
        setAnyView();
    }

    private void setAnyView() {
        btnTxt.setText(CalendariUtiles.anyFromDate(CalendariUtiles.selectedDate));
    }

    public void onItemClick(int pos, LocalDate date)
    {
        if(date != null)
        {
            CalendariUtiles.selectedDate = date;
            startActivity(new Intent(this, CalendariMensual.class));
        }
    }
}
