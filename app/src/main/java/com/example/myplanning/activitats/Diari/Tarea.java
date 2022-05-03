package com.example.myplanning.activitats.Diari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myplanning.R;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Llista.Usuario;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;

public class Tarea extends AppCompatActivity {

    private Usuario user = Usuario.getInstance();
    private Button btnCancelar;
    private Button btnPujar;
    private fireBaseController db = fireBaseController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_layout);
        initWidgets();
    }

    private void initWidgets() {
        btnPujar = findViewById(R.id.button);
        btnCancelar = findViewById(R.id.buttonCancelar);
    }

    public void pujarTascaAccio(View view){

        EditText txtActivitat = findViewById(R.id.edit_text_username);
        Spinner spinnerValueDia = findViewById(R.id.spinnerDay);
        Spinner spinnerValueMes = findViewById(R.id.spinnerMes);
        Spinner spinnerValueAny = findViewById(R.id.spinnerAny);
        Spinner spinnerValueHora = findViewById(R.id.spinnerHour);
        Spinner spinnerValueMinutos = findViewById(R.id.spinnerMins);

        Spinner spinnerActivitat = findViewById(R.id.spinnerSeleccioTarea);
        String activitatPenjar = (String) spinnerActivitat.getSelectedItem();

        String activitat = txtActivitat.getText().toString();
        Integer dia = (Integer) spinnerValueDia.getSelectedItem();
        Integer mes = (Integer) spinnerValueMes.getSelectedItem();
        Integer any = (Integer) spinnerValueAny.getSelectedItem();

        Integer hora = (Integer) spinnerValueHora.getSelectedItem();
        Integer minutos = (Integer) spinnerValueMinutos.getSelectedItem();

        LocalDateTime localDateTime =
                LocalDateTime.parse(any+"-"+mes+"-"+dia+"T"+hora+":"+minutos+":00");

        if(activitatPenjar.equals("Schedule")){
            db.setCollectUserScheduleDay(localDateTime,user.getNom(),activitat);

        }else if(activitatPenjar.equals("To-Do")){
            db.setCollectUserTodo(localDateTime,user.getNom(),activitat);

        }else{
            db.setCollectUserHomework(localDateTime,user.getNom(),activitat);

        }

        startActivity(new Intent(this, CalendariDiari.class));
    }

    public void cancelarAccio(View view){
        startActivity(new Intent(this, CalendariDiari.class));
    }
}
