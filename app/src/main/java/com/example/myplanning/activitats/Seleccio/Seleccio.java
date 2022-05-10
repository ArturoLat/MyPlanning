package com.example.myplanning.activitats.Seleccio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Anual.CalendariAnual;
import com.example.myplanning.activitats.Configuracio.Configuracio;
import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.activitats.Mensual.CalendariMensual;

public class Seleccio extends AppCompatActivity {
    private Button btnDiari;
    private Button btnSemanal;
    private Button btnMensual;
    private Button btnAnual;
    private Button btnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccio_layout);
        initWidgets();
    }

    private void initWidgets(){
        btnDiari = findViewById(R.id.btnDiari);
        btnMensual = findViewById(R.id.btnMensual);
        btnAnual = findViewById(R.id.btnAnual);
        btnConfig = findViewById(R.id.btnConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void diariAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }

    public void mensualAccio(View view){
        startActivity(new Intent(this, CalendariMensual.class));
    }

    public void anualAccio(View view){
        startActivity(new Intent(this, CalendariAnual.class));
    }

    public void configuracioAccio(View view){
        startActivity(new Intent(this, Configuracio.class));
    }
}
