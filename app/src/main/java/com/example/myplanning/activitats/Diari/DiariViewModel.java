package com.example.myplanning.activitats.Diari;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Item.HomeWork;
import com.example.myplanning.model.Item.Schedule;
import com.example.myplanning.model.Item.ToDo;
import com.example.myplanning.model.Usuari.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class DiariViewModel extends AndroidViewModel implements llistArrayObserver {

    private final MutableLiveData<ArrayList<Dades>> listDatosShedule;
    private final MutableLiveData<ArrayList<Dades>> listDatostoDo;
    private final MutableLiveData<ArrayList<Dades>> listDatosHomeWork;
    private Usuario user = com.example.myplanning.model.Usuari.Usuario.getInstance();
    private fireBaseController db;

    public DiariViewModel(Application app) {
        super(app);
        this.listDatosShedule = new MutableLiveData<>();
        this.listDatostoDo = new MutableLiveData<>();
        this.listDatosHomeWork = new MutableLiveData<>();
        db = new fireBaseController(this);

    }

    public void initDades(LocalDateTime date){
        db.getCollectUserHomeWork(this.user.getNom(),date);
        db.getCollectUserTodo(this.user.getNom(),date);
        db.getCollectUserSchedule(this.user.getNom(),date);

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

    public void addSchedule(String act, boolean donit, String data){
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
}
