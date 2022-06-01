package com.example.myplanning.model.Item;

import com.example.myplanning.model.Item.Dades;

import java.time.LocalDateTime;

public class ToDo extends Dades {

    public ToDo(String activitat, Boolean done, String date, int color) {
        super(activitat, done, date, color);
    }
}
