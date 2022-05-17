package com.example.myplanning.activitats.Diari;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Item.HomeWork;
import com.example.myplanning.model.Item.Schedule;
import com.example.myplanning.model.Item.ToDo;

import java.util.ArrayList;
import java.util.Map;

public class DiariViewModel extends AndroidViewModel implements llistArrayObserver {

    private final MutableLiveData<ArrayList<Schedule>> listDatosShedule;
    private final MutableLiveData<ArrayList<ToDo>> listDatostoDo;
    private final MutableLiveData<ArrayList<HomeWork>> listDatosHomeWork;
    //private final MutableLiveData<String> mToast;

    public DiariViewModel(Application app) {
        super(app);
        this.listDatosShedule = new MutableLiveData<>();
        this.listDatostoDo = new MutableLiveData<>();
        this.listDatosHomeWork = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Schedule>> getListDatosShedule() {
        return listDatosShedule;
    }

    public LiveData<ArrayList<ToDo>> getListDatostoDo() {
        return listDatostoDo;
    }

    public LiveData<ArrayList<HomeWork>> getListDatosHomeWork() {
        return listDatosHomeWork;
    }

    @Override
    public void notificarSchedule(ArrayList<Schedule> dada) {

    }

    @Override
    public void notificarToDo(ArrayList<ToDo> dada) {

    }

    @Override
    public void notificarHomeWork(ArrayList<HomeWork> dada) {

    }
}
