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

import java.util.ArrayList;
import java.util.Map;

public class DiariViewModel extends AndroidViewModel implements llistArrayObserver {

    private final MutableLiveData<ArrayList<Dades>> listDatosShedule;
    private final MutableLiveData<ArrayList<Dades>> listDatostoDo;
    private final MutableLiveData<ArrayList<Dades>> listDatosHomeWork;
    private Usuario user = com.example.myplanning.model.Usuari.Usuario.getInstance();
    //private final MutableLiveData<String> mToast;

    public DiariViewModel(Application app) {
        super(app);
        this.listDatosShedule = new MutableLiveData<>();
        this.listDatostoDo = new MutableLiveData<>();
        this.listDatosHomeWork = new MutableLiveData<>();
        fireBaseController db = new fireBaseController(this);

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

    public void addSchedule(){


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
