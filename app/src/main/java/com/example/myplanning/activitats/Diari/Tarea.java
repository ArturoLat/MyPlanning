package com.example.myplanning.activitats.Diari;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myplanning.R;
import com.example.myplanning.db.db_Sqlite;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Usuari.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarea extends AppCompatActivity {

    private Usuario user = Usuario.getInstance();
    private Button btnCancelar;
    private Button btnPujar;
    private fireBaseController db;
    private db_Sqlite dbLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_layout);
        initWidgets();
        if(user != null){
            db = fireBaseController.getInstance();

        }else {
            dbLite = dbLite.getInstance();

        }
    }

    private void initWidgets() {
        btnPujar = findViewById(R.id.button);
        btnCancelar = findViewById(R.id.buttonCancelar);
    }

    public void pujarTascaAccio(View view){
        EditText txtActivitat = findViewById(R.id.txtTareaName);
        Spinner spinnerValueDia = findViewById(R.id.spinnerDay);
        Spinner spinnerValueMes = findViewById(R.id.spinnerMes);
        Spinner spinnerValueAny = findViewById(R.id.spinnerAny);
        Spinner spinnerValueHora = findViewById(R.id.spinnerHour);
        Spinner spinnerValueMinutos = findViewById(R.id.spinnerMins);

        Spinner spinnerActivitat = findViewById(R.id.spinnerSeleccioTarea);
        String activitatPenjar = spinnerActivitat.getSelectedItem().toString();

        String activitat = txtActivitat.getText().toString();
        Integer dia = Integer.parseInt(spinnerValueDia.getSelectedItem().toString());
        Integer mes = Integer.parseInt(spinnerValueMes.getSelectedItem().toString());
        Integer any = Integer.parseInt(spinnerValueAny.getSelectedItem().toString());

        Integer hora = Integer.parseInt(spinnerValueHora.getSelectedItem().toString());;
        Integer minutos = Integer.parseInt(spinnerValueMinutos.getSelectedItem().toString());;

        LocalDateTime localDateTime = LocalDateTime.of(any,mes,dia,hora,minutos,0);

        if(user != null){
            if(activitatPenjar.equals("Schedule")){
                db.setCollectUserSchedule(localDateTime, user.getNom(), activitat);

            }else if(activitatPenjar.equals("To-Do")){
                db.setCollectUserTodo(localDateTime, user.getNom(), activitat);

            }else{
                db.setCollectUserHomework(localDateTime, user.getNom(), activitat);

            }

        }else{
            if(activitatPenjar.equals("Schedule")){
                dbLite.insertSchedule(dbLite.getDatabase(),activitat,localDateTime.toString());
                //setCollectUserSchedule(localDateTime, user.getNom(), activitat);

            }else if(activitatPenjar.equals("To-Do")){
                dbLite.insertToDo(dbLite.getDatabase(),activitat,localDateTime.toString());

            }else{
                dbLite.insertTask(dbLite.getDatabase(),activitat,localDateTime.toString());

            }
        }
        startActivity(new Intent(this, CalendariDiari.class));
    }

    public void cancelarAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }
}
