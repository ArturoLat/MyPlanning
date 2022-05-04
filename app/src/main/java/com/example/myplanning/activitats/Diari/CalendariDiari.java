package com.example.myplanning.activitats.Diari;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Llista.Usuario;

public class CalendariDiari extends AppCompatActivity {

    private Button diaSetmanaTV;
    private RecyclerView scheduleRecycleView;
    private RecyclerView toDoRecycleView;
    private RecyclerView tasksRecycleView;
    private LocalDateTime diaActual = LocalDateTime.now();
    private Usuario user = Usuario.getInstance();

    private Map<String, Object> listDatosShedule = new HashMap<>();
    private Map<String, Object> listDatostoDo = new HashMap<>();
    private Map<String, Object> listDatosHomeWork = new HashMap<>();

    private fireBaseController db = fireBaseController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari_diari);
        initWidgets();

    }

    private void initWidgets() {
        this.diaSetmanaTV = findViewById(R.id.btnDia);
        this.scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        this.toDoRecycleView = findViewById(R.id.toDoRecycleView);
        this.tasksRecycleView = findViewById(R.id.tasksRecycleView);
        /*this.scheduleRecycleView.setLayoutManager(new LinearLayoutManager(this));
        this.toDoRecycleView.setLayoutManager(new LinearLayoutManager(this));
        this.tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));*/

        db.getCollectUserSchedule(user.getNom(),diaActual);
        db.getCollectUserTodo(user.getNom(),diaActual);
        db.getCollectUserHomeWork(user.getNom(),diaActual);

        /*ArrayList<String> listaFinalShedule = new ArrayList<>();
        ArrayList<String> listaFinaltoDo = new ArrayList<>();
        ArrayList<String> listFinalHomeWork = new ArrayList<>();

        Schedule tempShedule;
        ToDo tempTodo;
        HomeWork tempHomeWork;*/

        /*for(String currentKey : listDatosShedule.keySet()){
            tempShedule = (Schedule) listDatosShedule.get(currentKey);

            listaFinalShedule.add(tempShedule.toString());

        }
        for(String currentKey : listDatostoDo.keySet()){
            tempTodo = (ToDo) listDatostoDo.get(currentKey);

            listaFinaltoDo.add(tempTodo.toString());

        }
        for(String currentKey : listDatosHomeWork.keySet()){
            tempHomeWork = (HomeWork) listDatosHomeWork.get(currentKey);

            listFinalHomeWork.add(tempHomeWork.toString());

        }*/
        if(!listDatosShedule.isEmpty()){
            AdapterRecycler adapterSchedule = new AdapterRecycler(listDatosShedule,this);
            scheduleRecycleView.setAdapter(adapterSchedule);

        }
        if(!listDatostoDo.isEmpty()){
            AdapterRecycler adaptertoDo = new AdapterRecycler(listDatostoDo,this);
            toDoRecycleView.setAdapter(adaptertoDo);

        }
        if(!listDatosHomeWork.isEmpty()){
            AdapterRecycler adapterHomwWork = new AdapterRecycler(listDatosHomeWork,this);
            tasksRecycleView.setAdapter(adapterHomwWork);

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

    public void changeAdapter(Map<String, Object> adapter){

        if((adapter).getClass().isInstance("Schedule")){

            AdapterRecycler adapterSchedule = new AdapterRecycler(adapter,this);
            scheduleRecycleView.swapAdapter(adapterSchedule,true);

        }else if((adapter).getClass().isInstance("ToDo")){

            AdapterRecycler adaptertoDo = new AdapterRecycler(adapter,this);
            toDoRecycleView.swapAdapter(adaptertoDo,true);

        }else{
            AdapterRecycler adapterHomwWork = new AdapterRecycler(adapter,this);
            tasksRecycleView.swapAdapter(adapterHomwWork,true);

        }
    }


}