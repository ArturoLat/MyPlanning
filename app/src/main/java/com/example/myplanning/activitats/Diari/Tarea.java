package com.example.myplanning.activitats.Diari;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myplanning.R;
import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.db.db_Sqlite;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Usuari.Usuario;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarea extends AppCompatActivity {

    private Usuario user = Usuario.getInstance();
    private fireBaseController db;
    private db_Sqlite dbLite;
    private Button afegirEvent;
    private Button cancelarEvent;
    private TimePicker time;
    private EditText tareaText;
    private SpectrumPalette palette;
    private Spinner spinnerActivitat;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hora_picker);
        initWidgets();
        if(user != null){
            db = fireBaseController.getInstance();

        }else {
            dbLite = dbLite.getInstance();

        }

        int hora = time.getHour();
        int min = time.getMinute();

        palette.setOnColorSelectedListener(
                clr -> color = clr
        );

        palette.setSelectedColor(getResources().getColor(R.color.yellow));
        color = getResources().getColor(R.color.yellow);

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hora, int min) {
                timePicker.setHour(hora);
                timePicker.setMinute(min);
            }
        });
    }

    private void initWidgets(){
        afegirEvent = findViewById(R.id.btnAfegirEvent);
        cancelarEvent = findViewById(R.id.cancelarEvent);
        time = findViewById(R.id.rellotge);
        tareaText = findViewById(R.id.tasca);
        palette = findViewById(R.id.spectrumPaletteTasca);
        spinnerActivitat = findViewById(R.id.spinnerActivitat);
    }

    public void activitatAccio(View view){
        tareaText.setTextColor(color);
        LocalDateTime eventTime = LocalDateTime.of(CalendariUtiles.selectedDate,
                LocalTime.of(time.getHour(),time.getMinute()));
        Dades dada = new Dades(tareaText.getText().toString(), false, eventTime.toString(),color);
        dada.setColor(color);
        String activitatPenjar = spinnerActivitat.getSelectedItem().toString();
        if(user != null){
            if(activitatPenjar.equals("Schedule")){
                db.setCollectUserSchedule(eventTime, user.getNom(), tareaText.getText().toString());

            }else if(activitatPenjar.equals("To-Do")){
                db.setCollectUserTodo(eventTime, user.getNom(), tareaText.getText().toString());

            }else{
                db.setCollectUserHomework(eventTime, user.getNom(), tareaText.getText().toString());

            }

        }else{
            if(activitatPenjar.equals("Schedule")){
                dbLite.insertSchedule(dbLite.getDatabase(),tareaText.getText().toString(),eventTime.toString(),color);
                //setCollectUserSchedule(localDateTime, user.getNom(), activitat);

            }else if(activitatPenjar.equals("To-Do")){
                dbLite.insertToDo(dbLite.getDatabase(),tareaText.getText().toString(),eventTime.toString(),color);

            }else{
                dbLite.insertTask(dbLite.getDatabase(),tareaText.getText().toString(),eventTime.toString(),color);

            }
        };
        startActivity(new Intent(this, CalendariDiari.class));
    }

    public void cancelarAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }


}
