package com.example.myplanning.activitats.Diari;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.db.db_Sqlite;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Item.Valoracio;
import com.example.myplanning.model.Usuari.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class DiariViewModel extends AndroidViewModel implements llistArrayObserver {

    private final MutableLiveData<ArrayList<Dades>> listDatosShedule;
    private final MutableLiveData<ArrayList<Dades>> listDatostoDo;
    private final MutableLiveData<ArrayList<Dades>> listDatosHomeWork;
    private final MutableLiveData<ArrayList<Valoracio>> listDatosValoracio;

    private Usuario user = com.example.myplanning.model.Usuari.Usuario.getInstance();
    private fireBaseController db;
    private db_Sqlite dbSqlite;

    public DiariViewModel(Application app) {
        super(app);
        this.listDatosValoracio = new MutableLiveData<>();
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
    public Boolean existUser(){
        if(user != null){
            return true;
        }
        return false;
    }

    public void initDades(LocalDateTime date){
        if (user != null){
            db.getCollectUserHomeWork(this.user.getNom(),date);
            db.getCollectUserTodo(this.user.getNom(),date);
            db.getCollectUserSchedule(this.user.getNom(),date);
            db.getCollectUserValoracio(this.user.getNom(),date);

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

    public LiveData<ArrayList<Valoracio>> getListValoracio() {
        return listDatosValoracio;
    }

    public LiveData<ArrayList<Dades>> getListDatosHomeWork() {
        return listDatosHomeWork;
    }

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

    @Override
    public void notificarValoracio(ArrayList<Valoracio> dada) {
        this.listDatosValoracio.setValue(dada);
    }

    public boolean emptyRegVal(LocalDateTime dia) {
        return dbSqlite.existValoracio(dia);

    }

    public Valoracio getValoracioDia(LocalDateTime dia) {
        float val = 0;
        if (user != null){
            db.getCollectUserValoracio(user.getNom(), dia);
            return new Valoracio(0, dia);
        }else{
            val = dbSqlite.readValoracio(dia);

        }
        return new Valoracio(val, dia);
    }

    public void updateValoracio(LocalDateTime dia, float nota) {
        if(user != null){
            db.insertValoracio(dia,nota, user.getNom());

        }else{
            dbSqlite.updateValoracio(dia, nota);

        }
    }

    public void insertValoracio(LocalDateTime diaActual) {
        dbSqlite.insertValoracio(dbSqlite.getWritableDatabase(),diaActual.toString(),(float)0);


    }
}
