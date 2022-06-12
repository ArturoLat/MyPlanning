package com.example.myplanning.activitats.Diari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myplanning.R;
import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.db.db_Sqlite;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Usuari.Usuario;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class updateTarea extends AppCompatActivity {

    private Button actualitzaEvent;
    private Button cancelarEvent;
    private TimePicker time;
    private EditText tareaText;
    private SpectrumPalette palette;
    private fireBaseController db = fireBaseController.getInstance();
    private db_Sqlite dbLite = db_Sqlite.getInstance();
    private int color;
    private String acType;
    private LocalDateTime dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualitzar_tasca);
        initWidgets();
        if(Usuario.getInstance() != null){
            db = fireBaseController.getInstance();

        }else {
            dbLite = dbLite.getInstance();

        }

        //agafa dades de la tasca (ScheduleAdapter)
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            String act = intent.getStringExtra("act");
            String type = intent.getStringExtra("type");
            String time = intent.getStringExtra("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.dia = LocalDateTime.parse(time, formatter);

            color = intent.getIntExtra("color",-16777216);

            this.acType = type;
            tareaText.setText(act);
            tareaText.setTextColor(color);

            palette.setSelectedColor(color);

            this.time.setHour(dia.getHour());
            this.time.setMinute(dia.getMinute());

        }else{
            int hora = time.getHour();
            int min = time.getMinute();

            palette.setSelectedColor(getResources().getColor(R.color.black));
            color = getResources().getColor(R.color.black);


            time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int hora, int min) {
                    timePicker.setHour(hora);
                    timePicker.setMinute(min);
                }
            });
        }
    }
    private void initWidgets(){
        actualitzaEvent = findViewById(R.id.btnAc);
        cancelarEvent = findViewById(R.id.cancelarAc);
        time = findViewById(R.id.rellotgeAc);
        tareaText = findViewById(R.id.tascaAc);
        palette = findViewById(R.id.spectrumPaletteAc);
        palette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener(){
            @Override
            public void onColorSelected(int color) {
                setColor(color);

            }

        });

    }

    public void eliminarTasca(View view){
        if(Usuario.getInstance() != null){
            if(acType.equals("Schedule")){
                db.deleteSchedule(dia, Usuario.getInstance().getNom(), tareaText.getText().toString());

            }else if(acType.equals("To-Do")){
                db.deleteTodo(dia, Usuario.getInstance().getNom(), tareaText.getText().toString());

            }else{
                db.deleteTask(dia, Usuario.getInstance().getNom(), tareaText.getText().toString());

            }

        }else{
            if(acType.equals("Schedule")){
                //dbLite.deleteSchedule(dbLite.getDatabase(),tareaText.getText().toString(),dia.toString(),color);


            }else if(acType.equals("To-Do")){
                //dbLite.deleteToDo(dbLite.getDatabase(),tareaText.getText().toString(),dia.toString(),color);

            }else{
                //dbLite.deleteHomework(dbLite.getDatabase(),tareaText.getText().toString(),dia.toString(),color);

            }
        }
        startActivity(new Intent(this, CalendariDiari.class));

    }

    public void cancelarAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }

    public void activitatAccio(View view){

        LocalDateTime eventTime = LocalDateTime.of(CalendariUtiles.selectedDate,
                LocalTime.of(time.getHour(),time.getMinute()));
        Dades dada = new Dades(tareaText.getText().toString(), false, eventTime.toString(),color);
        if(Usuario.getInstance() != null){
            if(acType.equals("Schedule")){
                db.setCollectUserSchedule(eventTime, Usuario.getInstance().getNom(), tareaText.getText().toString(),color);

            }else if(acType.equals("To-Do")){
                db.setCollectUserTodo(eventTime, Usuario.getInstance().getNom(), tareaText.getText().toString(),color);

            }else{
                db.setCollectUserHomework(eventTime, Usuario.getInstance().getNom(), tareaText.getText().toString(),color);

            }

        }else{
            if(acType.equals("Schedule")){
                dbLite.insertSchedule(dbLite.getDatabase(),tareaText.getText().toString(),eventTime.toString(),color);
                //setCollectUserSchedule(localDateTime, user.getNom(), activitat);

            }else if(acType.equals("To-Do")){
                dbLite.insertToDo(dbLite.getDatabase(),tareaText.getText().toString(),eventTime.toString(),color);

            }else{
                dbLite.insertTask(dbLite.getDatabase(),tareaText.getText().toString(),eventTime.toString(),color);

            }
        }
        startActivity(new Intent(this, CalendariDiari.class));
    }
    private void setColor(int color){
        this.color = color;
    }
}
