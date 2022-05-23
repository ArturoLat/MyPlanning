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
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.*;
import com.example.myplanning.model.Usuari.Usuario;

public class CalendariDiari extends AppCompatActivity{

    private Button diaSetmanaTV;
    private static RecyclerView scheduleRecycleView;
    private static RecyclerView toDoRecycleView;
    private static RecyclerView tasksRecycleView;
    private Context parentContext;
    private DiariViewModel viewModel;
    private LocalDateTime diaActual = CalendariUtiles.selectedDate.atStartOfDay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentContext = this.getBaseContext();
        setContentView(R.layout.activity_calendari_diari);
        initWidgets();

    }

    private void initWidgets() {
        this.diaSetmanaTV = findViewById(R.id.btnDia);
        scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        toDoRecycleView = findViewById(R.id.toDoRecycleView);
        tasksRecycleView = findViewById(R.id.tasksRecycleView);
        scheduleRecycleView.setLayoutManager(new LinearLayoutManager(this));
        toDoRecycleView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));

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
        viewModel = new ViewModelProvider(this).get(DiariViewModel.class);

        final Observer<ArrayList<Dades>> observer = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                AdapterRecycler newAdapter = new AdapterRecycler(ac);
                scheduleRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Dades>> observer1 = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                AdapterRecycler newAdapter = new AdapterRecycler(ac);
                toDoRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Dades>> observer2 = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                AdapterRecycler newAdapter = new AdapterRecycler(ac);
                tasksRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getListDatosHomeWork().observe(this, observer2);
        viewModel.getListDatosShedule().observe(this, observer);
        viewModel.getListDatostoDo().observe(this,observer1);
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