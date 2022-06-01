package com.example.myplanning.activitats.Diari;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.db.db_Sqlite;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Item.HomeWork;
import com.example.myplanning.model.Item.Schedule;
import com.example.myplanning.model.Item.ToDo;
import com.example.myplanning.model.Usuari.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiariViewModel extends AndroidViewModel implements llistArrayObserver {

    private final MutableLiveData<ArrayList<Dades>> listDatosShedule;
    private final MutableLiveData<ArrayList<Dades>> listDatostoDo;
    private final MutableLiveData<ArrayList<Dades>> listDatosHomeWork;
    private Usuario user = com.example.myplanning.model.Usuari.Usuario.getInstance();
    private fireBaseController db;
    private db_Sqlite dbSqlite;

    public DiariViewModel(Application app) {
        super(app);
        this.listDatosShedule = new MutableLiveData<>();
        this.listDatostoDo = new MutableLiveData<>();
        this.listDatosHomeWork = new MutableLiveData<>();
        if(user != null){
            this.db = new fireBaseController(this);

        }else{
            dbSqlite = this.dbSqlite.getInstance();
            dbSqlite.setListener(this);

        }

    }

    public void initDades(LocalDateTime date){
        if (user != null){
            db.getCollectUserHomeWork(this.user.getNom(),date);
            db.getCollectUserTodo(this.user.getNom(),date);
            db.getCollectUserSchedule(this.user.getNom(),date);

        }else{
            Map<String, Dades> resposta;
            if(dbSqlite.readTask(date) != null){
                resposta = dbSqlite.readTask(date);
                mapToArray(resposta, "Task");
            }
            if(dbSqlite.readSchedule(date) != null){
                resposta = dbSqlite.readSchedule(date);
                mapToArray(resposta, "Schedule");
            }
            if(dbSqlite.readToDo(date) != null){
                resposta = dbSqlite.readToDo(date);
                mapToArray(resposta, "Todo");
            }
        }
    }

    private void mapToArray(Map<String, Dades> resposta, String opcio) {
        ArrayList<Dades> dades = new ArrayList<>(resposta.values());
        if(opcio == "Schedule"){
            this.listDatosShedule.setValue(dades);

        }else if(opcio == "Task"){
            this.listDatosHomeWork.setValue(dades);

        }else{
            this.listDatostoDo.setValue(dades);

        }
    }

    public LiveData<ArrayList<Dades>> getListDatosShedule() {
        return listDatosShedule;
    }

    public LiveData<ArrayList<Dades>> getListDatostoDo() {
        return listDatostoDo;
    }

    public LiveData<ArrayList<Dades>> getListDatosHomeWork() {
        return listDatosHomeWork;
    }

    /*public void addSchedule(String act, boolean donit, String data){
        Dades nou = new Dades(act,donit,data);
        if (nou != null){
            this.listDatosShedule.getValue().add(nou);
            this.listDatosShedule.setValue(listDatosShedule.getValue());
            db.setCollectUserSchedule(nou.getDate(),user.getNom(),act);

        }
    }

    public void addHomework(String act, boolean donit, String data){
        HomeWork nou = new HomeWork(act,donit,data);
        if (nou != null){
            this.listDatosHomeWork.getValue().add(nou);
            this.listDatosHomeWork.setValue(listDatosHomeWork.getValue());
            db.setCollectUserSchedule(nou.getDate(),user.getNom(),act);

        }
    }

    public void addTodo(String act, boolean donit, String data){
        ToDo nou = new ToDo(act,donit,data);
        if (nou != null){
            this.listDatostoDo.getValue().add(nou);
            this.listDatostoDo.setValue(listDatostoDo.getValue());
            db.setCollectUserSchedule(nou.getDate(),user.getNom(),act);

        }
    }*/

    @Override
    public void notificarSchedule(ArrayList<Dades> dada) {
        this.listDatosShedule.setValue(dada);

    }

    @Override
    public void notificarToDo(ArrayList<Dades> dada) {
        this.listDatostoDo.setValue(dada);

    }

    @Override
    public void notificarHomeWork(ArrayList<Dades> dada) {
        this.listDatosHomeWork.setValue(dada);

    }
}
