package com.example.myplanning.activitats.Diari;



import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.example.myplanning.model.Item.*;

public class CalendariDiari extends AppCompatActivity{

    private Button diaSetmanaTV;
    private static RecyclerView scheduleRecycleView;
    private static RecyclerView toDoRecycleView;
    private static RecyclerView tasksRecycleView;
    private RatingBar nota;
    private Context parentContext;
    private DiariViewModel viewModel;
    private LocalDateTime diaActual = CalendariUtiles.selectedDate.atStartOfDay();
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DiariViewModel.class);
        parentContext = this.getBaseContext();
        setContentView(R.layout.activity_calendari_diari);
        initWidgets();
        setLiveDataObservers();
        this.initRating();

    }

    private void initWidgets() {
        this.diaSetmanaTV = findViewById(R.id.btnDia);
        this.nota = findViewById(R.id.notaDia);
        scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        toDoRecycleView = findViewById(R.id.toDoRecycleView);
        tasksRecycleView = findViewById(R.id.tasksRecycleView);
        scheduleRecycleView.setLayoutManager(new LinearLayoutManager(this));
        toDoRecycleView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void initRating(){
        if(!viewModel.existUser()){
            //verifica si hi ha registre de valoracio
            if(!viewModel.emptyRegVal(diaActual)){
                //si no existeix s'inicialitza el valor de val a 0
                nota.setRating(0);
                //s'insereix registre a la base de dades (valor inicial a 0)
                viewModel.insertValoracio(diaActual);

            }

        }
        //get valoracio
        Valoracio val = viewModel.getValoracioDia(diaActual);
        if(val != null){
            nota.setRating(val.getValoracio());

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDiaView();

    }

    public void diaSeleccioAccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }

    private void setDiaView() {
        String diaSetmana = CalendariUtiles.selectedDate.format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.LONG));
        diaSetmanaTV.setText(diaSetmana);
        diaActual = CalendariUtiles.selectedDate.atStartOfDay();
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel.initDades(diaActual);

        nota.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                viewModel.updateValoracio(diaActual,v);

            }
        });

        final Observer<ArrayList<Valoracio>> observer0 = new Observer<ArrayList<Valoracio>>() {
            @Override
            public void onChanged(ArrayList<Valoracio> ac) {
                nota.setRating(ac.get(0).getValoracio());
            }
        };

        final Observer<ArrayList<Dades>> observer = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                ArrayList<Dades> ordenada = ordenarLlista(ac);
                ScheduleAdapter newAdapter = new ScheduleAdapter(ordenada, "Schedule");
                scheduleRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Dades>> observer1 = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                ArrayList<Dades> ordenada = ordenarLlista(ac);
                ScheduleAdapter newAdapter = new ScheduleAdapter(ordenada,"To-Do");
                toDoRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Dades>> observer2 = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                ArrayList<Dades> ordenada = ordenarLlista(ac);
                ScheduleAdapter newAdapter = new ScheduleAdapter(ordenada, "Tasks");
                tasksRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getListDatosHomeWork().observe(this, observer2);
        viewModel.getListDatosShedule().observe(this, observer);
        viewModel.getListDatostoDo().observe(this,observer1);
        viewModel.getListValoracio().observe(this,observer0);
    }

    public void nextDayAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.plusDays(1);
        setDiaView();
        viewModel.initDades(diaActual);
        this.initRating();

    }

    public void prevDayAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.minusDays(1);
        setDiaView();
        viewModel.initDades(diaActual);
        this.initRating();

    }

    public void notaAction(View view){
        Toast toast = Toast.makeText(this,"Nota", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void toDoAction(View view){
        startActivity(new Intent(this, Tarea.class));

    }

    public void happiAction(View view){
        Toast toast = Toast.makeText(this,"Happy", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void taskAction(View view){
        startActivity(new Intent(this, Tarea.class));
    }

    public void scheduleAction(View view){
        startActivity(new Intent(this, Tarea.class));
    }

    public ArrayList<Dades> ordenarLlista(ArrayList<Dades> llistaEvents) {
        Collections.sort(llistaEvents, Comparator.comparing(e -> e.getDate()));
        return llistaEvents;
    }



    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

}