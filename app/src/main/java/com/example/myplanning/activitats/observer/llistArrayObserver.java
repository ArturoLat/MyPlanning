package com.example.myplanning.activitats.observer;

import com.example.myplanning.model.Item.HomeWork;
import com.example.myplanning.model.Item.Schedule;
import com.example.myplanning.model.Item.ToDo;

import java.util.ArrayList;
import java.util.Map;

public interface llistArrayObserver {

    public void notificarSchedule(ArrayList<Schedule> dada);
    public void notificarToDo(ArrayList<ToDo> dada);
    public void notificarHomeWork(ArrayList<HomeWork> dada);

}
