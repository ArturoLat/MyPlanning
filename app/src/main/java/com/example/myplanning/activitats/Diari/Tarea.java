package com.example.myplanning.activitats.Diari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myplanning.R;

public class Tarea extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnPujar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_layout);
        initWidgets();
    }

    private void initWidgets() {
        btnPujar = findViewById(R.id.btnPujarTasca);
        btnCancelar = findViewById(R.id.btnCancelar);
    }

    public void pujarTascaAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }

    public void cancelarAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }
}
