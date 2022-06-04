package com.example.myplanning.model.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Valoracio {
    private float valoracio;
    private final LocalDateTime date;

    public Valoracio(float valoracio, LocalDateTime datetext) {

        this.valoracio = valoracio;
        this.date = datetext;
    }

    public float getValoracio() {
        return valoracio;
    }

    public void setValoracio(float valoracio) {
        this.valoracio = valoracio;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
