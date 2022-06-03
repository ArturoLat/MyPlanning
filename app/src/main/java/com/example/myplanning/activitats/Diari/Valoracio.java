package com.example.myplanning.activitats.Diari;

import java.time.LocalDate;

public class Valoracio {
    private float valoracio;
    private final LocalDate date;

    public Valoracio(float valoracio, LocalDate date) {
        this.valoracio = valoracio;
        this.date = date;
    }

    public float getValoracio() {
        return valoracio;
    }

    public void setValoracio(float valoracio) {
        this.valoracio = valoracio;
    }

    public LocalDate getDate() {
        return date;
    }
}
