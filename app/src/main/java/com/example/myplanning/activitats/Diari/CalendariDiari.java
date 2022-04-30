package com.example.myplanning.activitats.Diari;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.activitats.Mensual.MensualAdapter;
import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;

public class CalendariDiari extends AppCompatActivity{


    private Button diaSetmanaTV;
    private Button diaSeleccio;
    RecyclerView scheduleRecycleView;
    RecyclerView toDoRecycleView;
    RecyclerView tasksRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari_diari);
        initWidgets();
    }

    private void initWidgets() {
        diaSetmanaTV = findViewById(R.id.btnDia);
        this.scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        this.toDoRecycleView = findViewById(R.id.toDoRecycleView);
        this.tasksRecycleView = findViewById(R.id.tasksRecycleView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDiaView();
    }

    public void diaSeleccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }

    private void setDiaView() {
        String diaSetmana = CalendariUtiles.selectedDate.format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.LONG));
        diaSetmanaTV.setText(diaSetmana);
    }

    public void nextDayAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.plusDays(1);
        setDiaView();

    }

    public void prevDayAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.minusDays(1);
        setDiaView();
    }

    public void notaAction(View view){
        Toast toast = Toast.makeText(this,"Nota", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toDoAction(View view){
        Toast toast = Toast.makeText(this,"To-Do", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void happiAction(View view){
        Toast toast = Toast.makeText(this,"Happy", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void taskAction(View view){
        startActivity(new Intent(this, Tarea.class));
    }

    public void scheduleAction(View view){
        Toast toast = Toast.makeText(this,"Schedule", Toast.LENGTH_SHORT);
        toast.show();
    }

}