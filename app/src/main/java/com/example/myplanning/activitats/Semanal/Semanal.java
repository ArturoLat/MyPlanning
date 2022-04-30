package com.example.myplanning.activitats.Semanal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;

public class Semanal extends AppCompatActivity {

    private Button btnWeekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semanal_layout);
        initWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initWidgets(){
        btnWeekly = findViewById(R.id.btnWeeklyPlanner);
    }

    public void weeklyPlannerAccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }
}
