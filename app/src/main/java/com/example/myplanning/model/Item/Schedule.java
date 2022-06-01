package com.example.myplanning.model.Item;

import com.example.myplanning.model.Item.Dades;

import java.time.LocalDateTime;

public class Schedule extends Dades {

    public Schedule(String activitat, Boolean done, String date, int color) {
        super(activitat, done, date, color);
    }
}
