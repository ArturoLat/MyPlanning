package com.example.myplanning.activitats.observer;

import java.util.Map;

public interface llistArrayObserver {

    public void notificarSchedule(Map<String, Object> dada) throws NoSuchFieldException, IllegalAccessException;
    public void notificarToDo(Map<String, Object> dada);
    public void notificarHomeWork(Map<String, Object> dada);

}
