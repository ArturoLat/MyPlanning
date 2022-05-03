package com.example.myplanning.activitats.Diari;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Map;

import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Llista.Schedule;
import com.example.myplanning.model.Llista.HomeWork;
import com.example.myplanning.model.Llista.ToDo;
import com.example.myplanning.model.Llista.Usuario;

public class CalendariDiari extends AppCompatActivity{

    private Button diaSetmanaTV;
    private RecyclerView scheduleRecycleView;
    private RecyclerView toDoRecycleView;
    private RecyclerView tasksRecycleView;

    private Usuario user = Usuario.getInstance();

    private long time;

    private Map<String, Schedule> listDatosShedule;
    private Map<String, ToDo> listDatostoDo;
    private Map<String, HomeWork> listDatosHomeWork;

    private fireBaseController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari_diari);
        initWidgets();
        db.getInstance();

    }

    private void initWidgets() {
        this.diaSetmanaTV = findViewById(R.id.btnDia);
        this.scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        this.toDoRecycleView = findViewById(R.id.toDoRecycleView);
        this.tasksRecycleView = findViewById(R.id.tasksRecycleView);
        //this.scheduleRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //this.toDoRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //this.tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));

        /*listDatosShedule = db.getCollectUserScheduleDay(time, user.getNom());
        listDatostoDo = db.getCollectUserTodoDay(time, user.getNom());
        listDatosHomeWork = db.getCollectUserHomeWorkDay(time, user.getNom());*/

        ArrayList<String> listaFinalShedule = new ArrayList<>();
        ArrayList<String> listaFinaltoDo = new ArrayList<>();
        ArrayList<String> listFinalHomeWork = new ArrayList<>();

        /*Schedule tempShedule;
        ToDo tempTodo;
        HomeWork tempHomeWork;

        for(String currentKey : listDatosShedule.keySet()){
            tempShedule = listDatosShedule.get(currentKey);

            listaFinalShedule.add(tempShedule.toString());

        }
        for(String currentKey : listDatostoDo.keySet()){
            tempTodo = listDatostoDo.get(currentKey);

            listaFinaltoDo.add(tempTodo.toString());

        }
        for(String currentKey : listDatosHomeWork.keySet()){
            tempHomeWork = listDatosHomeWork.get(currentKey);

            listFinalHomeWork.add(tempHomeWork.toString());

        }

        AdapterRecycler adapterSchedule = new AdapterRecycler(listaFinalShedule,this);
        AdapterRecycler adaptertoDo = new AdapterRecycler(listaFinaltoDo,this);
        AdapterRecycler adapterHomwWork = new AdapterRecycler(listFinalHomeWork,this);

        scheduleRecycleView.setAdapter(adapterSchedule);
        toDoRecycleView.setAdapter(adaptertoDo);
        tasksRecycleView.setAdapter(adapterHomwWork);*/
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