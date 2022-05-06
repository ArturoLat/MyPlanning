package com.example.myplanning.model.Item;

import com.example.myplanning.model.Item.Dades;

import java.time.LocalDateTime;

public class ToDo extends Dades {

    public ToDo(String activitat, Boolean done, LocalDateTime date) {
        super(activitat, done, date);
    }
}
